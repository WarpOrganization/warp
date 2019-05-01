package net.warpgame.engine.graphics.utility;

import static org.lwjgl.util.vma.Vma.*;

/**
 * @author MarconZet
 * Created 01.05.2019
 */
public class VkUtil {
    public static  int fixVmaMemoryUsage(int usage){
        switch (usage){
            case VMA_MEMORY_USAGE_UNKNOWN: return 0;
            case VMA_MEMORY_USAGE_GPU_ONLY: return 1;
            case VMA_MEMORY_USAGE_CPU_ONLY: return 2;
            case VMA_MEMORY_USAGE_CPU_TO_GPU: return 3;
            case VMA_MEMORY_USAGE_GPU_TO_CPU: return 4;
        }
        throw new RuntimeException(usage + " is unknown usage type");
    }
}
