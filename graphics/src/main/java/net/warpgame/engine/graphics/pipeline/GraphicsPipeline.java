package net.warpgame.engine.graphics.pipeline;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.VulkanTask;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.resource.mesh.Vertex;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 13.04.2019
 */
@Service
@Profile("graphics")
public class GraphicsPipeline implements CreateAndDestroy {
    private long graphicsPipeline;

    private List<Long> shaderModules = new ArrayList<>();
    private long pipelineLayout;
    private long descriptorSetLayout;

    private Device device;
    private SwapChain swapChain;
    private RenderPass renderPass;

    public GraphicsPipeline(Device device, SwapChain swapChain, RenderPass renderPass) {
        this.device = device;
        this.swapChain = swapChain;
        this.renderPass = renderPass;
    }

    @Override
    public void create() {
        VkPipelineShaderStageCreateInfo.Buffer shaderStages = VkPipelineShaderStageCreateInfo.create(2);
        try {
            shaderStages.get(0).set(loadShader("program/scene/prototype/vert.spv", VK_SHADER_STAGE_VERTEX_BIT));
            shaderStages.get(1).set(loadShader("program/scene/prototype/frag.spv", VK_SHADER_STAGE_FRAGMENT_BIT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VkPipelineVertexInputStateCreateInfo vertexInputInfo = VkPipelineVertexInputStateCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
                .pNext(NULL)
                .pVertexBindingDescriptions(Vertex.getBindingDescription())
                .pVertexAttributeDescriptions(Vertex.getAttributeDescriptions());

        VkPipelineInputAssemblyStateCreateInfo inputAssembly = VkPipelineInputAssemblyStateCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO)
                .pNext(NULL)
                .topology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
                .primitiveRestartEnable(false);

        VkViewport.Buffer viewport = VkViewport.create(1)
                .x(0.0f)
                .y(0.0f)
                .width((float) swapChain.getExtent().width())
                .height((float) swapChain.getExtent().height())
                .minDepth(0.0f)
                .maxDepth(1.0f);

        VkOffset2D offset = VkOffset2D.create()
                .set(0, 0);

        VkRect2D.Buffer scissor = VkRect2D.create(1)
                .offset(offset)
                .extent(swapChain.getExtent());

        VkPipelineViewportStateCreateInfo viewportState = VkPipelineViewportStateCreateInfo.create()
                .pNext(NULL)
                .sType(VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO)
                .viewportCount(1)
                .pViewports(viewport)
                .scissorCount(1)
                .pScissors(scissor);

        VkPipelineRasterizationStateCreateInfo rasterizer = VkPipelineRasterizationStateCreateInfo.create()
                .pNext(NULL)
                .sType(VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
                .depthClampEnable(false)
                .rasterizerDiscardEnable(false)
                .polygonMode(VK_POLYGON_MODE_FILL)
                .lineWidth(1.0f)
                .cullMode(VK_CULL_MODE_NONE)
                .frontFace(VK_FRONT_FACE_COUNTER_CLOCKWISE)
                .depthBiasEnable(false)
                .depthBiasConstantFactor(0.0f)
                .depthBiasClamp(0.0f)
                .depthBiasSlopeFactor(0.0f);

        VkPipelineMultisampleStateCreateInfo multisampling = VkPipelineMultisampleStateCreateInfo.create()
                .pNext(NULL)
                .sType(VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
                .sampleShadingEnable(false)
                .rasterizationSamples(VK_SAMPLE_COUNT_1_BIT)
                .minSampleShading(1.0f)
                .pSampleMask(null)
                .alphaToCoverageEnable(false)
                .alphaToOneEnable(false);

        VkPipelineDepthStencilStateCreateInfo depthStencil = VkPipelineDepthStencilStateCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO)
                .depthTestEnable(true)
                .depthWriteEnable(true)
                .depthCompareOp(VK_COMPARE_OP_LESS)
                .depthBoundsTestEnable(false)
                .minDepthBounds(0.0f)
                .maxDepthBounds(1.0f)
                .stencilTestEnable(false);

        VkPipelineColorBlendAttachmentState.Buffer colorBlendAttachment = VkPipelineColorBlendAttachmentState.create(1)
                .colorWriteMask(VK_COLOR_COMPONENT_R_BIT | VK_COLOR_COMPONENT_G_BIT | VK_COLOR_COMPONENT_B_BIT | VK_COLOR_COMPONENT_A_BIT)
                .blendEnable(false)
                .srcColorBlendFactor(VK_BLEND_FACTOR_ONE)
                .dstColorBlendFactor(VK_BLEND_FACTOR_ZERO)
                .colorBlendOp(VK_BLEND_OP_ADD)
                .srcAlphaBlendFactor(VK_BLEND_FACTOR_ONE)
                .dstAlphaBlendFactor(VK_BLEND_FACTOR_ZERO)
                .alphaBlendOp(VK_BLEND_OP_ADD);

        VkPipelineColorBlendStateCreateInfo colorBlending = VkPipelineColorBlendStateCreateInfo.create()
                .pNext(NULL)
                .sType(VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO)
                .logicOpEnable(false)
                .logicOp(VK_LOGIC_OP_COPY)
                .pAttachments(colorBlendAttachment);
        colorBlending.blendConstants().put(new float[]{0f, 0f, 0f, 0f}).flip();

        IntBuffer pDynamicStates = BufferUtils.createIntBuffer(1).put(0, VK_DYNAMIC_STATE_LINE_WIDTH);
        VkPipelineDynamicStateCreateInfo dynamicState = VkPipelineDynamicStateCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO)
                .pDynamicStates(pDynamicStates);

        descriptorSetLayout = createDescriptorSetLayout();
        VkPipelineLayoutCreateInfo pipelineLayoutInfo = VkPipelineLayoutCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO)
                .pSetLayouts(BufferUtils.createLongBuffer(1).put(0, descriptorSetLayout))
                .pPushConstantRanges(null);

        LongBuffer pPipelineLayout = BufferUtils.createLongBuffer(1);
        int err = vkCreatePipelineLayout(device.get(), pipelineLayoutInfo, null, pPipelineLayout);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create pipeline layout", err);
        }
        pipelineLayout = pPipelineLayout.get(0);

        VkGraphicsPipelineCreateInfo.Buffer pipelineInfo = VkGraphicsPipelineCreateInfo.create(1)
                .sType(VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
                .pStages(shaderStages)
                .pVertexInputState(vertexInputInfo)
                .pInputAssemblyState(inputAssembly)
                .pViewportState(viewportState)
                .pRasterizationState(rasterizer)
                .pMultisampleState(multisampling)
                .pDepthStencilState(depthStencil)
                .pColorBlendState(colorBlending)
                .pDynamicState(dynamicState)
                .layout(pipelineLayout)
                .renderPass(renderPass.get())
                .subpass(0)
                .basePipelineHandle(VK_NULL_HANDLE)
                .basePipelineIndex(0);

        LongBuffer pPipeline = BufferUtils.createLongBuffer(1);
        err = vkCreateGraphicsPipelines(device.get(), VK_NULL_HANDLE, pipelineInfo, null, pPipeline);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create pipeline", err);
        }
        graphicsPipeline = pPipeline.get(0);
    }

    @Override
    public void destroy() {
        vkDestroyPipeline(device.get(), graphicsPipeline, null);
        vkDestroyPipelineLayout(device.get(), pipelineLayout, null);
        for (Long shaderModule : shaderModules) {//TODO no need to destroy shader module during resize
            vkDestroyShaderModule(device.get(), shaderModule, null);
        }
        vkDestroyDescriptorSetLayout(device.get(), descriptorSetLayout, null);
    }

    private long createDescriptorSetLayout() {
        VkDescriptorSetLayoutBinding.Buffer layoutBinding = VkDescriptorSetLayoutBinding.create(2);
        layoutBinding.get(0)
                .binding(0)
                .descriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
                .descriptorCount(1)
                .stageFlags(VK_SHADER_STAGE_VERTEX_BIT)
                .pImmutableSamplers(null);
        layoutBinding.get(1)
                .binding(1)
                .descriptorCount(1)
                .descriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
                .pImmutableSamplers(null)
                .stageFlags(VK_SHADER_STAGE_FRAGMENT_BIT);

        VkDescriptorSetLayoutCreateInfo layoutCreateInfo = VkDescriptorSetLayoutCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO)
                .pBindings(layoutBinding);

        LongBuffer p = BufferUtils.createLongBuffer(1);
        int err = vkCreateDescriptorSetLayout(device.get(), layoutCreateInfo, null, p);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to create descriptor set layout", err);
        }
        return p.get(0);
    }

    private VkPipelineShaderStageCreateInfo loadShader(String classPath, int stage) throws IOException {
        ByteBuffer shaderCode = ioResourceToByteBuffer(classPath);
        VkShaderModuleCreateInfo moduleCreateInfo = VkShaderModuleCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
                .pNext(NULL)
                .pCode(shaderCode)
                .flags(0);
        LongBuffer pShaderModule = BufferUtils.createLongBuffer(1);
        int err = vkCreateShaderModule(device.get(), moduleCreateInfo, null, pShaderModule);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create shader module", err);
        }
        long shaderModule = pShaderModule.get(0);
        shaderModules.add(shaderModule);
        return VkPipelineShaderStageCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
                .stage(stage)
                .module(shaderModule)
                .pName(memUTF8("main"));
    }

    private static ByteBuffer ioResourceToByteBuffer(String resource) throws IOException {
        URL url = VulkanTask.class.getResource(resource);
        File file = new File(url.getFile());
        if (!file.isFile()) {
            throw new RuntimeException(String.format("%s is not a file", file.toString()));
        }
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        fc.close();
        fis.close();
        return buffer;
    }
}
