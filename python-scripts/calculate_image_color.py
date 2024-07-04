import cv2
import sys


def extract_color_histogram(image, bins=(8, 8, 8)):
    # 提取图像的颜色直方图
    hist = cv2.calcHist([image], [0, 1, 2], None, bins, [0, 256, 0, 256, 0, 256])
    cv2.normalize(hist, hist)
    return hist.flatten()


def load_image_and_extract_histograms(image_path):
    # 加载图像并提取颜色直方图
    hist = []
    image = cv2.imread(image_path)
    if image is not None:
        hist = extract_color_histogram(image)
    return hist


if __name__ == "__main__":
    # 获取传递的参数
    prefix = sys.argv[1]
    args = sys.argv[2:]
    for arg in args:
        hist = load_image_and_extract_histograms(prefix + arg)
        #  序列化为 JSON 格式
        print(hist.tolist())
