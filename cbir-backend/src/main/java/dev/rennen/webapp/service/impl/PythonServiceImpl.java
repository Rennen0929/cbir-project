package dev.rennen.webapp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.dto.MatchingResultResponseVo;
import dev.rennen.webapp.service.PythonService;
import dev.rennen.webapp.utils.JsonUtil;
import dev.rennen.webapp.utils.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class PythonServiceImpl implements PythonService {
    @Override
    public List<String> batchCalcColor(List<String> imagePaths, String pathPrefix) {
        List<String> command = Lists.newArrayList();
        command.add("python");
        command.add(CommonConstant.CALCULATE_COLOR_SCRIPT_PATH);
        command.add(pathPrefix);
        List<String> handledPaths = imagePaths.stream().map(PathUtil::pathConverter).toList();
        command.addAll(handledPaths);

        return execCommand(command);
    }

    @Override
    public List<MatchingResultResponseVo> matchImagesByFeature(String vector, String datas, String dimension) {
        List<String> command = Lists.newArrayList();
        command.add("python");
        command.add(CommonConstant.MATCH_SCRIPT_PATH);
        command.add(dimension);
        List<String> inputs = Lists.newArrayList();
        inputs.add(vector);
        inputs.add(datas);

        List<String> result = execCommandAndInput(command, inputs);
        if (CollectionUtil.isEmpty(result) || result.size() != 1) {
            throw new RuntimeException("[matchImagesByColor]匹配结果不符合预期");
        }
        String resultStr = result.get(0);
        return JsonUtil.parseJSONArray(resultStr, new TypeReference<>() {});
    }

    @Override
    public List<String> batchCalcTexture(List<String> imagePaths, String imageFilePrefix) {
        List<String> command = Lists.newArrayList();
        command.add("python");
        command.add(CommonConstant.CALCULATE_TEXTURE_SCRIPT_PATH);
        command.add(imageFilePrefix);
        List<String> handledPaths = imagePaths.stream().map(PathUtil::pathConverter).toList();
        command.addAll(handledPaths);

        return execCommand(command);
    }



    private List<String> execCommand(List<String> command) {

        List<String> result = Lists.newArrayList();

        try {
            // 创建 ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(command);

            // 启动进程
            Process process = pb.start();

            // 读取 Python 脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;

        } catch (Exception e) {
            log.error("调用 Python 脚本出错", e);
        }
        return result;
    }

    private List<String> execCommandAndInput(List<String> command, List<String> inputs) {
        List<String> result = Lists.newArrayList();

        try {
            // 创建 ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(command);

            // 启动进程
            Process process = pb.start();

            // 写入标准输入
            try (Writer writer = new OutputStreamWriter(process.getOutputStream())) {
                for (String input : inputs) {
                    writer.write(input);
                    writer.write(System.lineSeparator());
                }
            }

            // 读取 Python 脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;

        } catch (Exception e) {
            log.error("调用 Python 脚本出错", e);
        }
        return result;
    }


}
