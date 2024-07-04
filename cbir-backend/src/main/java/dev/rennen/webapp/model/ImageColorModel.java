package dev.rennen.webapp.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName image_color
 */
@Data
public class ImageColorModel implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 图像ID，关联 image_all_data 表
     */
    private Long imageId;

    /**
     * 
     */
    private String color;

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
        ImageColorModel other = (ImageColorModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getImageId() == null ? other.getImageId() == null : this.getImageId().equals(other.getImageId()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getImageId() == null) ? 0 : getImageId().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
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
        sb.append(", color=").append(color);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}