package pl.warp.engine.graphics.mesh;

/**
 * @author Jaca777
 *         Created 2016-08-03 at 00
 */
public class Sphere extends VAOMesh {

    private float radius;
    private int segmentsW;
    private int segmentsH;

    public Sphere(float radius, int segmentsW, int segmentsH) {
        super(2 * segmentsW * (segmentsH - 1) * 3, (segmentsW + 1) * (segmentsH + 1));
        this.radius = radius;
        this.segmentsW = segmentsW;
        this.segmentsH = segmentsH;
        create();
    }


    protected void create() {

        float[] vertices = new float[this.vertices * 3];
        float[] normals = new float[this.vertices * 3];
        int[] indices = new int[this.indices];

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

