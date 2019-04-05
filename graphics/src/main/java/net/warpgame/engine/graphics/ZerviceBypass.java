package net.warpgame.engine.graphics;

/**
 * @author MarconZet
 * Created 05.04.2019
 */
public class ZerviceBypass {

    public static void main(String... args){
        Instance instance = new Instance(null);
        VulkanTask vulkanTask = new VulkanTask(instance);

        System.out.println("Starting");
        vulkanTask.onInit();
        System.out.println("Running");
        while (true) {
            break;
        }
        System.out.println("Closing");
        vulkanTask.onClose();

    }
}
