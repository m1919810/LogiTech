package me.matl114.logitech.utils.UtilClass.EntityClass;

import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public final class TransformationBuilder {
    private AxisAngle4f firstRotation = new AxisAngle4f(0.0F, 0.0F, 1.0F, 0.0F);
    private AxisAngle4f secondRotation = new AxisAngle4f(0.0F, 0.0F, 1.0F, 0.0F);
    private Vector3f scaling = new Vector3f(1.0F, 1.0F, 1.0F);
    private Vector3f translation = new Vector3f(0.0F, 0.0F, 0.0F);

    public TransformationBuilder() {}

    //    public TransformationBuilder firstRotation(RotationFace face, float angle) {
    //        this.firstRotation = new AxisAngle4f((float)Math.toRadians((double)angle), face.getX(), face.getY(),
    // face.getZ());
    //        return this;
    //    }
    //
    //    public TransformationBuilder secondRotation(RotationFace face, float angle) {
    //        this.secondRotation = new AxisAngle4f((float)Math.toRadians((double)angle), face.getX(), face.getY(),
    // face.getZ());
    //        return this;
    //    }

    public TransformationBuilder scale(float scaleX, float scaleY, float scaleZ) {
        this.scaling = new Vector3f(scaleX, scaleY, scaleZ);
        return this;
    }

    public TransformationBuilder translation(float deltaX, float deltaY, float deltaZ) {
        this.translation = new Vector3f(deltaX, deltaY, deltaZ);
        return this;
    }

    public Transformation build() {
        return new Transformation(this.translation, this.firstRotation, this.scaling, this.secondRotation);
    }
}
