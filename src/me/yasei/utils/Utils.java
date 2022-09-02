package me.yasei.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
//	Functions by: PrzemoVi
	public static void replaceFileContents(File f, String s) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
		writer.write(s);
		writer.close();
	}

	public static void replaceInFile(File file, String replace, String with) {
		Path path = file.toPath();
		Charset charset = StandardCharsets.UTF_8;
		try {
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(replace, with);
			Files.write(path, content.getBytes(charset));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readEverythingFromFile(File f) throws IOException {
		/*FileReader reader = new FileReader(f,StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(reader);

		String s = "";

		String line = "";
		while ((line = br.readLine()) != null) {
			s += line;
		}*/
		
		byte[] bytes = Files.readAllBytes(f.toPath());
		String s = new String(bytes,StandardCharsets.UTF_8);

		/*br.close();
		reader.close();*/

		return s;
	}

	public static List<String> readLinesFromFile(File f) throws IOException {
		FileReader reader = new FileReader(f,StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(reader);

		String line;
		List<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		br.close();
		reader.close();

		return lines;
	}
	
	public static String readEverythingFromURL(String s) throws IOException {
		URL url = new URL(s);
		URLConnection con = url.openConnection();
		con.setConnectTimeout(3000);
		con.setReadTimeout(3000);
		/*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

		String entirefile = "";

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			entirefile += inputLine;
		}
		in.close();*/
		
		InputStream is = con.getInputStream();
		byte[] bytes = is.readAllBytes();
		String entirefile = new String(bytes,StandardCharsets.UTF_8);

		return entirefile;
	}
}
