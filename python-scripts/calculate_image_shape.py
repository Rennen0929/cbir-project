import sys

import cv2
import numpy as np


def load_image(image_path):
    return cv2.imread(image_path)


def rgb_to_gray(image):
    return cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)


def gaussian_blur(image, kernel_size=(5, 5), sigma=0):
    return cv2.GaussianBlur(image, kernel_size, sigma)


def sobel_gradient(image):
    grad_x = cv2.Sobel(image, cv2.CV_64F, 1, 0, ksize=3)
    grad_y = cv2.Sobel(image, cv2.CV_64F, 0, 1, ksize=3)
    return grad_x, grad_y


def compute_edge_angles(grad_x, grad_y):
    angles = np.arctan2(grad_y, grad_x) * (180 / np.pi)
    angles[angles < 0] += 360
    return angles


def compute_edge_direction_histogram(angles, bins=36):
    hist, bin_edges = np.histogram(angles, bins=bins, range=(0, 360))
    hist = hist / np.sum(hist)  # Normalize
    hist = cv2.GaussianBlur(hist.reshape(1, -1), (5, 5), 0).flatten()  # Smooth
    return hist


def extract_features(image):
    image = load_image(image)
    gray_image = rgb_to_gray(image)
    blurred_image = gaussian_blur(gray_image)
    grad_x, grad_y = sobel_gradient(blurred_image)
    angles = compute_edge_angles(grad_x, grad_y)
    hist = compute_edge_direction_histogram(angles)
    return hist


if __name__ == "__main__":
    # 获取传递的参数
    prefix = sys.argv[1]
    args = sys.argv[2:]

    for arg in args:
        features = extract_features(prefix + arg)
        #  序列化为 JSON 格式
        print(features.tolist())

