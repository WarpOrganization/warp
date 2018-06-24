package net.warpgame.engine.graphics.animation.colladaloader;


import net.warpgame.engine.graphics.animation.colladaloader.datastructures.*;
import net.warpgame.engine.graphics.animation.colladaloader.xmlparser.XmlNode;
import net.warpgame.engine.graphics.animation.colladaloader.xmlparser.XmlParser;

import java.io.InputStream;

public class ColladaLoader {

	public static AnimatedModelData loadColladaModel(InputStream colladaData, int maxWeights) {
		XmlNode node = XmlParser.loadXmlFile(colladaData);

		SkinLoader skinLoader = new SkinLoader(node.getChild("library_controllers"), maxWeights);
		SkinningData skinningData = skinLoader.extractSkinData();

		SkeletonLoader jointsLoader = new SkeletonLoader(node.getChild("library_visual_scenes"), skinningData.jointOrder);
		SkeletonData jointsData = jointsLoader.extractBoneData();

		GeometryLoader g = new GeometryLoader(node.getChild("library_geometries"), skinningData.verticesSkinData);
		MeshData meshData = g.extractModelData();

		return new AnimatedModelData(meshData, jointsData);
	}

	public static AnimationData loadColladaAnimation(InputStream colladaData) {
		XmlNode node = XmlParser.loadXmlFile(colladaData);
		XmlNode animNode = node.getChild("library_animations");
		XmlNode jointsNode = node.getChild("library_visual_scenes");
		ColladaAnimationLoader loader = new ColladaAnimationLoader(animNode, jointsNode);
		AnimationData animData = loader.extractAnimation();
		return animData;
	}

}
