package pl.warp.engine.graphics.mesh;


/**
 * Copyright 2013 Dennis Ippel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

/**
 * @author Dennis Ippel, Jaca777
 *         Created 2016-08-03 at 00
 */
public class Sphere extends VAOMesh {

    private float radius;
    private int segmentsW;
    private int segmentsH;

    public Sphere(float radius, int segmentsW, int segmentsH) {
        super((segmentsW + 1) * (segmentsH + 1));
        this.radius = radius;
        this.segmentsW = segmentsW;
        this.segmentsH = segmentsH;
        createSphere();
    }


    protected void createSphere() {
        int numVertices = indices;
        int numIndices = 2 * segmentsW * (segmentsH - 1) * 3;

        float[] vertices = new float[numVertices * 3];
        float[] normals = new float[numVertices * 3];
        int[] indices = new int[numIndices];

        int i, j;
        int vertIndex = 0, index = 0;
        final float normLen = 1.0f / radius;

        for (j = 0; j <= segmentsH; ++j) {
            float horAngle = (float) (Math.PI * j / segmentsH);
            float z = radius * (float) Math.cos(horAngle);
            float ringRadius = radius * (float) Math.sin(horAngle);

            for (i = 0; i <= segmentsW; ++i) {
                float verAngle = (float) (2.0f * Math.PI * i / segmentsW);
                float x = ringRadius * (float) Math.cos(verAngle);
                float y = ringRadius * (float) Math.sin(verAngle);

                normals[vertIndex] = x * normLen;
                vertices[vertIndex++] = x;
                normals[vertIndex] = z * normLen;
                vertices[vertIndex++] = z;
                normals[vertIndex] = y * normLen;
                vertices[vertIndex++] = y;

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

        setVertexData(vertices);
        setNormalData(normals);
        setTexCoordData(textureCoords);
        setIndexData(indices);
    }
}

