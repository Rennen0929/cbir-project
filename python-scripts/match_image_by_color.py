import sys
import numpy as np
import json
from sklearn.metrics.pairwise import cosine_similarity


def calculate_similarity(pending_match_color_info, color_info_in_database):
    # 将字符串形式的直方图数据转换为NumPy数组
    pending_match_color_array = np.array(eval(pending_match_color_info))

    # 初始化输出列表
    output = []

    # 遍历数据库中的每张图片的直方图数据
    for entry in json.loads(color_info_in_database):
        image_id = entry['imageId']
        color_hist = np.array(eval(entry['color']))

        # 计算余弦相似度
        similarity = cosine_similarity([pending_match_color_array], [color_hist])[0][0]

        # 将结果添加到输出列表
        output.append({
            "imageId": image_id,
            "similarity": similarity
        })

    return output


# 示例用法
if __name__ == "__main__":
    pendingMatchColorInfo = sys.stdin.readline().strip()
    colorInfoInDatabase = sys.stdin.readline().strip()

    # 调用函数并打印结果
    results = calculate_similarity(pendingMatchColorInfo, colorInfoInDatabase)
    print(json.dumps(results, separators=(',', ':')))  # 打印格式化的JSON输出
