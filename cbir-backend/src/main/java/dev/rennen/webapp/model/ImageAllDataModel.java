package dev.rennen.webapp.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 用于存储所有的训练图片（train 文件夹下）
 * @TableName image_all_data
 */
@Data
public class ImageAllDataModel implements Serializable {
    /**
     * 主键，全局唯一标识
     */
    private Long id;

    /**
     * 路径，由类别 + 文件名组成，例如【/airplain/0001.png】
     */
    private String path;

    /**
     * 图像类别，例如 airplain 等
     */
    private String category;

    /**
     * 在每个类别下的序号
     */
    private Integer seqNum;

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
        ImageAllDataModel other = (ImageAllDataModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getSeqNum() == null ? other.getSeqNum() == null : this.getSeqNum().equals(other.getSeqNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getSeqNum() == null) ? 0 : getSeqNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", path=").append(path);
        sb.append(", category=").append(category);
        sb.append(", seqNum=").append(seqNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}