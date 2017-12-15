package pl.warp.engine.common.math;

import org.joml.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.NumberFormat;

/**
 * @author Jaca777
 * Created 2017-12-14 at 21
 * It was a long clicking.
 */
public class ImmutableMatrix4f extends Matrix4f {

    @Override
    public Matrix4f m00(float m00) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m01(float m01) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m02(float m02) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m03(float m03) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m10(float m10) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m11(float m11) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m12(float m12) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m13(float m13) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m20(float m20) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m21(float m21) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m22(float m22) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m23(float m23) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m30(float m30) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m31(float m31) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m32(float m32) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m33(float m33) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f identity() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix4f m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix4d m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix3f mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(AxisAngle4f axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(AxisAngle4d axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Quaternionf q) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Quaterniond q) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set3x3(Matrix4f mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul(Matrix4f right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul(Matrix4f right, Matrix4f dest) {
        return super.mul(right, dest);
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4f view) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4f view, Matrix4f dest) {
        return super.mulPerspectiveAffine(view, dest);
    }

    @Override
    public Matrix4f mulAffineR(Matrix4f right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulAffineR(Matrix4f right, Matrix4f dest) {
        return super.mulAffineR(right, dest);
    }

    @Override
    public Matrix4f mulAffine(Matrix4f right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulAffine(Matrix4f right, Matrix4f dest) {
        return super.mulAffine(right, dest);
    }

    @Override
    public Matrix4f mulOrthoAffine(Matrix4f view) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulOrthoAffine(Matrix4f view, Matrix4f dest) {
        return super.mulOrthoAffine(view, dest);
    }

    @Override
    public Matrix4f fma4x3(Matrix4f other, float otherFactor) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f fma4x3(Matrix4f other, float otherFactor, Matrix4f dest) {
        return super.fma4x3(other, otherFactor, dest);
    }

    @Override
    public Matrix4f add(Matrix4f other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f add(Matrix4f other, Matrix4f dest) {
        return super.add(other, dest);
    }

    @Override
    public Matrix4f sub(Matrix4f subtrahend) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f sub(Matrix4f subtrahend, Matrix4f dest) {
        return super.sub(subtrahend, dest);
    }

    @Override
    public Matrix4f mulComponentWise(Matrix4f other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulComponentWise(Matrix4f other, Matrix4f dest) {
        return super.mulComponentWise(other, dest);
    }

    @Override
    public Matrix4f add4x3(Matrix4f other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f add4x3(Matrix4f other, Matrix4f dest) {
        return super.add4x3(other, dest);
    }

    @Override
    public Matrix4f sub4x3(Matrix4f subtrahend) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f sub4x3(Matrix4f subtrahend, Matrix4f dest) {
        return super.sub4x3(subtrahend, dest);
    }

    @Override
    public Matrix4f mul4x3ComponentWise(Matrix4f other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul4x3ComponentWise(Matrix4f other, Matrix4f dest) {
        return super.mul4x3ComponentWise(other, dest);
    }

    @Override
    public Matrix4f set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(float[] m, int off) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(float[] m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(FloatBuffer buffer) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(ByteBuffer buffer) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public float determinant() {
        return super.determinant();
    }

    @Override
    public float determinant3x3() {
        return super.determinant3x3();
    }

    @Override
    public float determinantAffine() {
        return super.determinantAffine();
    }

    @Override
    public Matrix4f invert(Matrix4f dest) {
        return super.invert(dest);
    }

    @Override
    public Matrix4f invert() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertPerspective(Matrix4f dest) {
        return super.invertPerspective(dest);
    }

    @Override
    public Matrix4f invertPerspective() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertFrustum(Matrix4f dest) {
        return super.invertFrustum(dest);
    }

    @Override
    public Matrix4f invertFrustum() {
        return super.invertFrustum();
    }

    @Override
    public Matrix4f invertOrtho(Matrix4f dest) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertOrtho() {
        return super.invertOrtho();
    }

    @Override
    public Matrix4f invertPerspectiveView(Matrix4f view, Matrix4f dest) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertAffine(Matrix4f dest) {
        return super.invertAffine(dest);
    }

    @Override
    public Matrix4f invertAffine() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertAffineUnitScale(Matrix4f dest) {
        return super.invertAffineUnitScale(dest);
    }

    @Override
    public Matrix4f invertAffineUnitScale() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertLookAt(Matrix4f dest) {
        return super.invertLookAt(dest);
    }

    @Override
    public Matrix4f invertLookAt() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f transpose(Matrix4f dest) {
        return super.transpose(dest);
    }

    @Override
    public Matrix4f transpose3x3() {
        return super.transpose3x3();
    }

    @Override
    public Matrix4f transpose3x3(Matrix4f dest) {
        return super.transpose3x3(dest);
    }

    @Override
    public Matrix3f transpose3x3(Matrix3f dest) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f transpose() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translation(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translation(Vector3f offset) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setTranslation(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setTranslation(Vector3f xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Vector3f getTranslation(Vector3f dest) {
        return super.getTranslation(dest);
    }

    @Override
    public Vector3f getScale(Vector3f dest) {
        return super.getScale(dest);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String toString(NumberFormat formatter) {
        return super.toString(formatter);
    }

    @Override
    public Matrix4f get(Matrix4f dest) {
        return super.get(dest);
    }

    @Override
    public Matrix4d get(Matrix4d dest) {
        return super.get(dest);
    }

    @Override
    public Matrix3f get3x3(Matrix3f dest) {
        return super.get3x3(dest);
    }

    @Override
    public Matrix3d get3x3(Matrix3d dest) {
        return super.get3x3(dest);
    }

    @Override
    public AxisAngle4f getRotation(AxisAngle4f dest) {
        return super.getRotation(dest);
    }

    @Override
    public AxisAngle4d getRotation(AxisAngle4d dest) {
        return super.getRotation(dest);
    }

    @Override
    public Quaternionf getUnnormalizedRotation(Quaternionf dest) {
        return super.getUnnormalizedRotation(dest);
    }

    @Override
    public Quaternionf getNormalizedRotation(Quaternionf dest) {
        return super.getNormalizedRotation(dest);
    }

    @Override
    public Quaterniond getUnnormalizedRotation(Quaterniond dest) {
        return super.getUnnormalizedRotation(dest);
    }

    @Override
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return super.getNormalizedRotation(dest);
    }

    @Override
    public FloatBuffer get(FloatBuffer buffer) {
        return super.get(buffer);
    }

    @Override
    public FloatBuffer get(int index, FloatBuffer buffer) {
        return super.get(index, buffer);
    }

    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return super.get(buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        return super.get(index, buffer);
    }

    @Override
    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return super.getTransposed(buffer);
    }

    @Override
    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        return super.getTransposed(index, buffer);
    }

    @Override
    public ByteBuffer getTransposed(ByteBuffer buffer) {
        return super.getTransposed(buffer);
    }

    @Override
    public ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        return super.getTransposed(index, buffer);
    }

    @Override
    public float[] get(float[] arr, int offset) {
        return super.get(arr, offset);
    }

    @Override
    public float[] get(float[] arr) {
        return super.get(arr);
    }

    @Override
    public Matrix4f zero() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaling(float factor) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaling(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaling(Vector3f xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(float angle, Vector3f axis) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(AxisAngle4f axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(float angle, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationX(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationY(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationZ(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRotationXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRotationZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRotationYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(Quaternionf quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScale(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScale(Vector3f translation, Quaternionf quat, Vector3f scale) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleMulAffine(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz, Matrix4f m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleMulAffine(Vector3f translation, Quaternionf quat, Vector3f scale, Matrix4f m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotate(float tx, float ty, float tz, Quaternionf quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set3x3(Matrix3f mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Vector4f transform(Vector4f v) {
        return super.transform(v);
    }

    @Override
    public Vector4f transform(Vector4f v, Vector4f dest) {
        return super.transform(v, dest);
    }

    @Override
    public Vector4f transformProject(Vector4f v) {
        return super.transformProject(v);
    }

    @Override
    public Vector4f transformProject(Vector4f v, Vector4f dest) {
        return super.transformProject(v, dest);
    }

    @Override
    public Vector3f transformProject(Vector3f v) {
        return super.transformProject(v);
    }

    @Override
    public Vector3f transformProject(Vector3f v, Vector3f dest) {
        return super.transformProject(v, dest);
    }

    @Override
    public Vector3f transformPosition(Vector3f v) {
        return super.transformPosition(v);
    }

    @Override
    public Vector3f transformPosition(Vector3f v, Vector3f dest) {
        return super.transformPosition(v, dest);
    }

    @Override
    public Vector3f transformDirection(Vector3f v) {
        return super.transformDirection(v);
    }

    @Override
    public Vector3f transformDirection(Vector3f v, Vector3f dest) {
        return super.transformDirection(v, dest);
    }

    @Override
    public Vector4f transformAffine(Vector4f v) {
        return super.transformAffine(v);
    }

    @Override
    public Vector4f transformAffine(Vector4f v, Vector4f dest) {
        return super.transformAffine(v, dest);
    }

    @Override
    public Matrix4f scale(Vector3f xyz, Matrix4f dest) {
        return super.scale(xyz, dest);
    }

    @Override
    public Matrix4f scale(Vector3f xyz) {
        return super.scale(xyz);
    }

    @Override
    public Matrix4f scale(float xyz, Matrix4f dest) {
        return super.scale(xyz, dest);
    }

    @Override
    public Matrix4f scale(float xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scale(float x, float y, float z, Matrix4f dest) {
        return super.scale(x, y, z, dest);
    }

    @Override
    public Matrix4f scale(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleLocal(float x, float y, float z, Matrix4f dest) {
        return super.scaleLocal(x, y, z, dest);
    }

    @Override
    public Matrix4f scaleLocal(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateX(float ang, Matrix4f dest) {
        return super.rotateX(ang, dest);
    }

    @Override
    public Matrix4f rotateX(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateY(float ang, Matrix4f dest) {
        return super.rotateY(ang, dest);
    }

    @Override
    public Matrix4f rotateY(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateZ(float ang, Matrix4f dest) {
        return super.rotateZ(ang, dest);
    }

    @Override
    public Matrix4f rotateZ(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        return super.rotateXYZ(angleX, angleY, angleZ, dest);
    }

    @Override
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        return super.rotateAffineXYZ(angleX, angleY, angleZ, dest);
    }

    @Override
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        return super.rotateZYX(angleZ, angleY, angleX, dest);
    }

    @Override
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        return super.rotateAffineZYX(angleZ, angleY, angleX, dest);
    }

    @Override
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        return super.rotateYXZ(angleY, angleX, angleZ, dest);
    }

    @Override
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        return super.rotateAffineYXZ(angleY, angleX, angleZ, dest);
    }

    @Override
    public Matrix4f rotate(float ang, float x, float y, float z, Matrix4f dest) {
        return super.rotate(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotate(float ang, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffine(float ang, float x, float y, float z, Matrix4f dest) {
        return super.rotateAffine(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotateAffine(float ang, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineLocal(float ang, float x, float y, float z, Matrix4f dest) {
        return super.rotateAffineLocal(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotateAffineLocal(float ang, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translate(Vector3f offset) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translate(Vector3f offset, Matrix4f dest) {
        return super.translate(offset, dest);
    }

    @Override
    public Matrix4f translate(float x, float y, float z, Matrix4f dest) {
        return super.translate(x, y, z, dest);
    }

    @Override
    public Matrix4f translate(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translateLocal(Vector3f offset) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translateLocal(Vector3f offset, Matrix4f dest) {
        return super.translateLocal(offset, dest);
    }

    @Override
    public Matrix4f translateLocal(float x, float y, float z, Matrix4f dest) {
        return super.translateLocal(x, y, z, dest);
    }

    @Override
    public Matrix4f translateLocal(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return super.ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return super.ortho(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return super.orthoSymmetric(width, height, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return super.orthoSymmetric(width, height, zNear, zFar, dest);
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f ortho2D(float left, float right, float bottom, float top, Matrix4f dest) {
        return super.ortho2D(left, right, bottom, top, dest);
    }

    @Override
    public Matrix4f ortho2D(float left, float right, float bottom, float top) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho2D(float left, float right, float bottom, float top) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAlong(Vector3f dir, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAlong(Vector3f dir, Vector3f up, Matrix4f dest) {
        return super.lookAlong(dir, up, dest);
    }

    @Override
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4f dest) {
        return super.lookAlong(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAlong(Vector3f dir, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAt(Vector3f eye, Vector3f center, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up, Matrix4f dest) {
        return super.lookAt(eye, center, up, dest);
    }

    @Override
    public Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return super.lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAtLH(Vector3f eye, Vector3f center, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAtLH(Vector3f eye, Vector3f center, Vector3f up, Matrix4f dest) {
        return super.lookAtLH(eye, center, up, dest);
    }

    @Override
    public Matrix4f lookAtLH(Vector3f eye, Vector3f center, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return super.lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return super.perspective(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return super.perspective(fovy, aspect, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return super.perspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return super.perspectiveLH(fovy, aspect, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return super.frustum(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return super.frustum(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return super.frustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return super.frustumLH(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(Quaternionf quat, Matrix4f dest) {
        return super.rotate(quat, dest);
    }

    @Override
    public Matrix4f rotate(Quaternionf quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffine(Quaternionf quat, Matrix4f dest) {
        return super.rotateAffine(quat, dest);
    }

    @Override
    public Matrix4f rotateAffine(Quaternionf quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineLocal(Quaternionf quat, Matrix4f dest) {
        return super.rotateAffineLocal(quat, dest);
    }

    @Override
    public Matrix4f rotateAffineLocal(Quaternionf quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(AxisAngle4f axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(AxisAngle4f axisAngle, Matrix4f dest) {
        return super.rotate(axisAngle, dest);
    }

    @Override
    public Matrix4f rotate(float angle, Vector3f axis) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(float angle, Vector3f axis, Matrix4f dest) {
        return super.rotate(angle, axis, dest);
    }

    @Override
    public Vector4f unproject(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        return super.unproject(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Vector3f unproject(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        return super.unproject(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Vector4f unproject(Vector3f winCoords, int[] viewport, Vector4f dest) {
        return super.unproject(winCoords, viewport, dest);
    }

    @Override
    public Vector3f unproject(Vector3f winCoords, int[] viewport, Vector3f dest) {
        return super.unproject(winCoords, viewport, dest);
    }

    @Override
    public Matrix4f unprojectRay(float winX, float winY, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return super.unprojectRay(winX, winY, viewport, originDest, dirDest);
    }

    @Override
    public Matrix4f unprojectRay(Vector2f winCoords, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return super.unprojectRay(winCoords, viewport, originDest, dirDest);
    }

    @Override
    public Vector4f unprojectInv(Vector3f winCoords, int[] viewport, Vector4f dest) {
        return super.unprojectInv(winCoords, viewport, dest);
    }

    @Override
    public Vector4f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        return super.unprojectInv(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Matrix4f unprojectInvRay(Vector2f winCoords, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return super.unprojectInvRay(winCoords, viewport, originDest, dirDest);
    }

    @Override
    public Matrix4f unprojectInvRay(float winX, float winY, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return super.unprojectInvRay(winX, winY, viewport, originDest, dirDest);
    }

    @Override
    public Vector3f unprojectInv(Vector3f winCoords, int[] viewport, Vector3f dest) {
        return super.unprojectInv(winCoords, viewport, dest);
    }

    @Override
    public Vector3f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        return super.unprojectInv(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Vector4f project(float x, float y, float z, int[] viewport, Vector4f winCoordsDest) {
        return super.project(x, y, z, viewport, winCoordsDest);
    }

    @Override
    public Vector3f project(float x, float y, float z, int[] viewport, Vector3f winCoordsDest) {
        return super.project(x, y, z, viewport, winCoordsDest);
    }

    @Override
    public Vector4f project(Vector3f position, int[] viewport, Vector4f winCoordsDest) {
        return super.project(position, viewport, winCoordsDest);
    }

    @Override
    public Vector3f project(Vector3f position, int[] viewport, Vector3f winCoordsDest) {
        return super.project(position, viewport, winCoordsDest);
    }

    @Override
    public Matrix4f reflect(float a, float b, float c, float d, Matrix4f dest) {
        return super.reflect(a, b, c, d, dest);
    }

    @Override
    public Matrix4f reflect(float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4f dest) {
        return super.reflect(nx, ny, nz, px, py, pz, dest);
    }

    @Override
    public Matrix4f reflect(Vector3f normal, Vector3f point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(Quaternionf orientation, Vector3f point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(Quaternionf orientation, Vector3f point, Matrix4f dest) {
        return super.reflect(orientation, point, dest);
    }

    @Override
    public Matrix4f reflect(Vector3f normal, Vector3f point, Matrix4f dest) {
        return super.reflect(normal, point, dest);
    }

    @Override
    public Matrix4f reflection(float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(float nx, float ny, float nz, float px, float py, float pz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(Vector3f normal, Vector3f point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(Quaternionf orientation, Vector3f point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Vector4f getRow(int row, Vector4f dest) throws IndexOutOfBoundsException {
        return super.getRow(row, dest);
    }

    @Override
    public Vector4f getColumn(int column, Vector4f dest) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f normal() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f normal(Matrix4f dest) {
        return super.normal(dest);
    }

    @Override
    public Matrix3f normal(Matrix3f dest) {
        return super.normal(dest);
    }

    @Override
    public Matrix4f normalize3x3() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f normalize3x3(Matrix4f dest) {
        return super.normalize3x3(dest);
    }

    @Override
    public Matrix3f normalize3x3(Matrix3f dest) {
        return super.normalize3x3(dest);
    }

    @Override
    public Vector4f frustumPlane(int plane, Vector4f planeEquation) {
        return super.frustumPlane(plane, planeEquation);
    }

    @Override
    public Vector3f frustumCorner(int corner, Vector3f point) {
        return super.frustumCorner(corner, point);
    }

    @Override
    public Vector3f perspectiveOrigin(Vector3f origin) {
        return super.perspectiveOrigin(origin);
    }

    @Override
    public float perspectiveFov() {
        return super.perspectiveFov();
    }

    @Override
    public float perspectiveNear() {
        return super.perspectiveNear();
    }

    @Override
    public float perspectiveFar() {
        return super.perspectiveFar();
    }

    @Override
    public Vector3f frustumRayDir(float x, float y, Vector3f dir) {
        return super.frustumRayDir(x, y, dir);
    }

    @Override
    public Vector3f positiveZ(Vector3f dir) {
        return super.positiveZ(dir);
    }

    @Override
    public Vector3f normalizedPositiveZ(Vector3f dir) {
        return super.normalizedPositiveZ(dir);
    }

    @Override
    public Vector3f positiveX(Vector3f dir) {
        return super.positiveX(dir);
    }

    @Override
    public Vector3f normalizedPositiveX(Vector3f dir) {
        return super.normalizedPositiveX(dir);
    }

    @Override
    public Vector3f positiveY(Vector3f dir) {
        return super.positiveY(dir);
    }

    @Override
    public Vector3f normalizedPositiveY(Vector3f dir) {
        return super.normalizedPositiveY(dir);
    }

    @Override
    public Vector3f originAffine(Vector3f origin) {
        return super.originAffine(origin);
    }

    @Override
    public Vector3f origin(Vector3f origin) {
        return super.origin(origin);
    }

    @Override
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d, Matrix4f dest) {
        return super.shadow(light, a, b, c, d, dest);
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4f dest) {
        return super.shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    @Override
    public Matrix4f shadow(Vector4f light, Matrix4f planeTransform, Matrix4f dest) {
        return super.shadow(light, planeTransform, dest);
    }

    @Override
    public Matrix4f shadow(Vector4f light, Matrix4f planeTransform) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4f planeTransform, Matrix4f dest) {
        return super.shadow(lightX, lightY, lightZ, lightW, planeTransform, dest);
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4f planeTransform) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f billboardCylindrical(Vector3f objPos, Vector3f targetPos, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f billboardSpherical(Vector3f objPos, Vector3f targetPos, Vector3f up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f billboardSpherical(Vector3f objPos, Vector3f targetPos) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport, Matrix4f dest) {
        return super.pick(x, y, width, height, viewport, dest);
    }

    @Override
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public boolean isAffine() {
        return super.isAffine();
    }

    @Override
    public Matrix4f swap(Matrix4f other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY, Matrix4f dest) {
        return super.arcball(radius, centerX, centerY, centerZ, angleX, angleY, dest);
    }

    @Override
    public Matrix4f arcball(float radius, Vector3f center, float angleX, float angleY, Matrix4f dest) {
        return super.arcball(radius, center, angleX, angleY, dest);
    }

    @Override
    public Matrix4f arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f arcball(float radius, Vector3f center, float angleX, float angleY) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustumAabb(Vector3f min, Vector3f max) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f projectedGridRange(Matrix4f projector, float sLower, float sUpper, Matrix4f dest) {
        return super.projectedGridRange(projector, sLower, sUpper, dest);
    }

    @Override
    public Matrix4f perspectiveFrustumSlice(float near, float far, Matrix4f dest) {
        return super.perspectiveFrustumSlice(near, far, dest);
    }

    @Override
    public Matrix4f orthoCrop(Matrix4f view, Matrix4f dest) {
        return super.orthoCrop(view, dest);
    }

    @Override
    public Matrix4f trapezoidCrop(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3f outMin, Vector3f outMax) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f transformAab(Vector3f min, Vector3f max, Vector3f outMin, Vector3f outMax) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }
}
