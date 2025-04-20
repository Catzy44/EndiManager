package me.przemovi.resourcepack;

import java.io.IOException;
import java.util.HexFormat;

import me.przemovi.EndiManager;
import me.przemovi.utils.Utils;

public class ResourcePack {
	private String url;
	private String hashUrl;
	private String hash;
	private byte[] hashBytes;

	public ResourcePack(String url, String hashUrl) {
		this.url = url;
		this.hashUrl = hashUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHashUrl() {
		return hashUrl;
	}

	public void setHashUrl(String hashUrl) {
		this.hashUrl = hashUrl;
	}

	long refreshingStarted = -1;

	public String getHash() {
		while (refreshingStarted > -1 && System.currentTimeMillis() - refreshingStarted < 10000) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return hash;
	}

	public byte[] getHashBytes() {
		return hashBytes;
	}

	public boolean refreshHash() throws IOException {
		return refreshHash(0);
	}
	public boolean refreshHash(int attemp) throws IOException {
		try {
			refreshingStarted = System.currentTimeMillis();
			hash = Utils.readEverythingFromURL(hashUrl);
			hashBytes = HexFormat.of().parseHex(hash);
			refreshingStarted = -1;
		} catch (Exception e) {
			EndiManager.debug("[RP] Refreshing hash FAILED!");
			EndiManager.debug(hashUrl);
			if(attemp < 3) {
				EndiManager.debug("[RP] Attemp #"+attemp+"...");
				try {Thread.sleep(500);} catch (InterruptedException e1) {}
				return refreshHash(attemp+1);
			}
			throw e;
		}

		return true;
	}
}
