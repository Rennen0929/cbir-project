package dev.rennen.webapp.common.constants;

import java.util.List;

public class CommonConstant {

    /**
     * 每个类别的图片数量
     */
    public static final int NUM_IMAGES_PER_CATEGORY = 100;

    /**
     * 图片的类别
     */
    public static final List<String> CATEGORIES = List.of(
            "airplane", "automobile", "bird", "cat", "deer", "dog", "frog", "horse", "ship", "truck"
    );

    /**
     * 批量计算处理数量值
     */
    public static final int BATCH_PROCESS_SIZE = 1000;

    /**
     * 计算单个图片的颜色信息，脚本位置
     */
    public static final String CALCULATE_COLOR_SCRIPT_PATH = "C:\\cbir-project\\python-scripts\\calculate_image_color.py";

    /**
     * 计算单个图片的颜色信息，脚本位置
     */
    public static final String CALCULATE_TEXTURE_SCRIPT_PATH = "C:\\cbir-project\\python-scripts\\calculate_image_texture.py";

    /**
     * 计算单个图片的颜色信息，脚本位置
     */
    public static final String CALCULATE_SHAPE_SCRIPT_PATH = "C:\\cbir-project\\python-scripts\\calculate_image_shape.py";

    /**
     * 匹配图片颜色信息，脚本位置
     */
    public static final String MATCH_COLOR_SCRIPT_PATH = "C:\\cbir-project\\python-scripts\\calculate_image_color.py";

    /**
     * 根据特征向量得出图片相似度，，脚本位置
     */
    public static final String MATCH_SCRIPT_PATH = "C:\\cbir-project\\python-scripts\\match_image_by_vector.py";

    /**
     * 图片文件存储前缀
     */
    public static final String IMAGE_FILE_PREFIX = "C:\\cbir-project\\cifar-10-png\\train";

    /**
     * 图片文件上传位置前缀
     */
    public static final String IMAGE_UPLOAD_PREFIX = "C:\\cbir-project\\uploaded-images";

    /**
     * 默认图片格式
     */
    public static final String DEFAULT_IMAGE_FORMAT = ".png";

    /**
     * 返回结果的大小
     */
    public static final int RETURN_SIZE = 12;

    /**
     * 图床展示路径前缀
     */
    public static final String IMAGE_HOST_URL_PREFIX = "https://img.rennen.dev/i/cbir-project";
}
