package pl.warp.engine.graphics.mesh.shapes;

import pl.warp.engine.graphics.rendering.scene.mesh.SceneMesh;

/**
 * @author Jaca777
 *         Created 2016-08-03 at 00
 */
public class SphereBuilder {

    public static SceneMesh createShape(int segmentsH, int segmentsW, float radius) {

        int verticesN = (segmentsW + 1) * (segmentsH + 1);
        int indicesN = 2 * segmentsW * (segmentsH - 1) * 3;

        float[] vertices = new float[verticesN * 3];
        float[] normals = new float[verticesN * 3];
        int[] indices = new int[indicesN];

        int i, j;
        int vertIndex = 0, index = 0;

        for (j = 0; j <= segmentsH; ++j) {
            float horAngle = (float) (Math.PI * j / segmentsH);
            float z = (float) Math.cos(horAngle);
            float ringRadius = (float) Math.sin(horAngle);

            for (i = 0; i <= segmentsW; ++i) {
                float verAngle = (float) (2.0f * Math.PI * i / segmentsW);
                float x = ringRadius * (float) Math.cos(verAngle);
                float y = ringRadius * (float) Math.sin(verAngle);

                normals[vertIndex] = x;
                vertices[vertIndex++] = x * radius;
                normals[vertIndex] = z;
                vertices[vertIndex++] = z * radius;
                normals[vertIndex] = y;
                vertices[vertIndex++] = y * radius;

                if (i > 0 && j > 0) {
                    int a = (segmentsW + 1) * j + i;
                    int b = (segmentsW + 1) * j + i - 1;
                    int c = (segmentsW + 1) * (j - 1) + i - 1;
                    int d = (segmentsW + 1) * (j - 1) + i;

                    if (j == segmentsH) {
                        indices[index++] = a;
                        indices[index++] = c;
                        indices[index++] = d;
                    } else if (j == 1) {
                        indices[index++] = a;
                        indices[index++] = b;
                        indices[index++] = c;
                    } else {
                        indices[index++] = a;
                        indices[index++] = b;
                        indices[index++] = c;
                        indices[index++] = a;
                        indices[index++] = c;
                        indices[index++] = d;
                    }
                }
            }
        }

        int numUvs = (segmentsH + 1) * (segmentsW + 1) * 2;
        float[] textureCoords = new float[numUvs];
        numUvs = 0;
        for (j = 0; j <= segmentsH; ++j) {
            for (i = segmentsW; i >= 0; --i) {
                textureCoords[numUvs++] = (float) i / segmentsW;
                textureCoords[numUvs++] = (float) j / segmentsH;
            }
        }

        return new SceneMesh(vertices, textureCoords, normals, indices);
    }
}

