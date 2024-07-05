package dev.rennen.webapp.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName image_shape
 */
@Data
public class ImageShapeModel implements Serializable {
    /**
     * 记录主键（自增）
     */
    private Long id;

    /**
     * 关联键
     */
    private Long imageId;

    /**
     * 形状信息
     */
    private String shape;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ImageShapeModel other = (ImageShapeModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getImageId() == null ? other.getImageId() == null : this.getImageId().equals(other.getImageId()))
            && (this.getShape() == null ? other.getShape() == null : this.getShape().equals(other.getShape()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getImageId() == null) ? 0 : getImageId().hashCode());
        result = prime * result + ((getShape() == null) ? 0 : getShape().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", imageId=").append(imageId);
        sb.append(", shape=").append(shape);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}