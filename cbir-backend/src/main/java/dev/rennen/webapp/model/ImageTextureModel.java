package dev.rennen.webapp.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName image_texture
 */
@Data
public class ImageTextureModel implements Serializable {
    /**
     * 主键，记录 ID
     */
    private Long id;

    /**
     * 关联 image_all_data
     */
    private Long imageId;

    /**
     * 纹理信息，JSON 格式
     */
    private String texture;

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
        ImageTextureModel other = (ImageTextureModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getImageId() == null ? other.getImageId() == null : this.getImageId().equals(other.getImageId()))
            && (this.getTexture() == null ? other.getTexture() == null : this.getTexture().equals(other.getTexture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getImageId() == null) ? 0 : getImageId().hashCode());
        result = prime * result + ((getTexture() == null) ? 0 : getTexture().hashCode());
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
        sb.append(", texture=").append(texture);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}