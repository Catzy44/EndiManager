package me.catzy44.tasks;

public class TasksManager {
	public static void startTasks() {
		LoginMessageSenderTask.getInstance().start();
		ActionBarSenderTask.getInstance().start();
		SpawnEffectApplierTask.getInstance().start();
		//NetherRoofHardMode.getInstance().start();
	}
	public static void stopTasks() {
		LoginMessageSenderTask.getInstance().stop();
		ActionBarSenderTask.getInstance().stop();
		SpawnEffectApplierTask.getInstance().stop();
		//NetherRoofHardMode.getInstance().stop();
	}
}
