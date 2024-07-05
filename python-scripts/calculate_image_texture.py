import sys

import numpy as np
from skimage.feature.texture import graycomatrix, graycoprops
from skimage import io, color


def extract_features(image_path):
    # 读取图像并转换为灰度图像
    image = io.imread(image_path)
    gray_image = color.rgb2gray(image)

    # 将灰度级降低到8个区间
    gray_image = (gray_image * 8).astype(np.uint8)

    # 确保灰度值在0到7之间
    gray_image = np.clip(gray_image, 0, 7)

    # 计算灰度共生矩阵
    glcm = graycomatrix(gray_image, distances=[1], angles=[0, np.pi / 4, np.pi / 2, 3 * np.pi / 4], levels=8,
                        symmetric=True, normed=True)

    # 计算纹理特征
    contrast = graycoprops(glcm, 'contrast').flatten()  # 对比度
    dissimilarity = graycoprops(glcm, 'dissimilarity').flatten()  # 不相似性
    homogeneity = graycoprops(glcm, 'homogeneity').flatten()  # 同质性
    energy = graycoprops(glcm, 'energy').flatten()  # 能量
    correlation = graycoprops(glcm, 'correlation').flatten()  # 相关性
    asm = graycoprops(glcm, 'ASM').flatten()  # 平均二次幂

    # 组合特征
    features = np.concatenate([contrast, dissimilarity, homogeneity, energy, correlation, asm])

    return features


if __name__ == "__main__":
    # 获取传递的参数
    prefix = sys.argv[1]
    args = sys.argv[2:]
    for arg in args:
        features = extract_features(prefix + arg)
        #  序列化为 JSON 格式
        print(features.tolist())


