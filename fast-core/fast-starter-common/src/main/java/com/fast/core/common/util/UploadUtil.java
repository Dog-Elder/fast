package com.fast.core.common.util;

import cn.hutool.core.io.FileUtil;
import com.fast.core.common.domain.domain.AttachBO;
import com.fast.core.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件上传
 **/
@Slf4j
@Component
public class UploadUtil {

    /**
     * 应用名
     **/
    private static String APPLY_NAME;

    /**
     * win上传地址路径
     */
    public static String PROFILE_WIN;

    /**
     * linux上传地址路径
     */
    public static String PROFILE_LINUX;

    @Value(value = "${fast.apply-name}")
    public void setAPPLY_NAME(String applyName) {
        UploadUtil.APPLY_NAME = applyName;
    }

    @Value("${attach.profile-win}")
    public void setPROFILE_WIN(String profileWin) {
        UploadUtil.PROFILE_WIN = profileWin;
    }

    @Value("${attach.profile-linux}")
    public void setPROFILE_LINUX(String profileLinux) {
        UploadUtil.PROFILE_LINUX = profileLinux;
    }

    /**
     * 缓存文件桶 记录
     * <k:文件桶名 , v:<目录名,目录对应的日期文件夹名>>
     **/
    private static final Map<String, Map<String, Set<String>>> DOCUMENT_BARREL =
            new ConcurrentHashMap<String, Map<String, Set<String>>>();

    /**
     * 附件位置 (profile-win/profile-win)+bucketName+directory+yyyy-MM-dd
     *
     * @param files:      文件数组
     * @param bucketName: 文件桶(类)
     * @param directory:  目录(子类)
     * @Description: 附件上传
     * @return: com.xxxxx.common.core.domain.AttachVO
     **/
    public static List<AttachBO> upload(MultipartFile[] files
            , @NotBlank(message = "文件桶名不能为空") String bucketName
            , @NotBlank(message = "目录名不能为空") String directory) {
        //判空
        for (MultipartFile file : files) {
            if (ObjectUtils.isEmpty(files) || file.getSize() <= 0) {
                throw new ServiceException("文件为空");
            }
        }
        MultipartFile files1 = files[0];
        LocalDate now = LocalDate.now();

        //判断是否是win系统
        String profilePath = Util.isWin ? PROFILE_WIN : PROFILE_LINUX;
        StringBuilder uploadPath = new StringBuilder();
        uploadPath.append(profilePath);

        //判断桶是否在缓存里
        uploadPath.append("/").append(bucketName);
        if (!DOCUMENT_BARREL.containsKey(bucketName)) {
            //判断文件夹是否存在
            if (!FileUtil.exist(uploadPath.toString())) {
                FileUtil.mkdir(uploadPath.toString());
            }
            DOCUMENT_BARREL.put(bucketName, new HashMap<>());
        }

        //判断目录是否在缓存里
        uploadPath.append("/").append(directory);
        if (!DOCUMENT_BARREL.get(bucketName).containsKey(directory)) {
            //判断文件夹是否存在
            if (!FileUtil.exist(uploadPath.toString())) {
                FileUtil.mkdir(uploadPath.toString());
            }
            Map<String, Set<String>> stringSetMap = DOCUMENT_BARREL.get(bucketName);
            stringSetMap.putIfAbsent(directory, new HashSet<>());
        }

        //判断日期文件夹是否在缓存
        uploadPath.append("/").append(now);
        if (!DOCUMENT_BARREL.get(bucketName).get(directory).contains(now.toString())) {
            //判断文件夹是否存在
            if (!FileUtil.exist(uploadPath.toString())) {
                FileUtil.mkdir(uploadPath.toString());
            }
            DOCUMENT_BARREL.get(bucketName).get(directory).add(now.toString());
        }
        List<AttachBO> attachVOS = new ArrayList<AttachBO>();
        //处理文件上传
        uploadPath.append("/");
        //附件存放地址
        StringBuilder attachAddress = new StringBuilder();
        attachAddress.append("/")
                .append(bucketName)
                .append("/")
                .append(directory)
                .append("/")
                .append(now)
                .append("/");
        for (MultipartFile file : files) {
            String oldName = file.getOriginalFilename();
            assert oldName != null;
            String fileName = oldName.substring(0, oldName.lastIndexOf("."));
            String suffix = oldName.substring(oldName.lastIndexOf("."));
            String newName = fileName + "_" + UUID.randomUUID().toString() + suffix;
            try {
                file.transferTo(new File(uploadPath.toString(), newName));
                attachVOS.add(new AttachBO()
                        .setAttachName(fileName)
                        .setAttachSuffix(suffix)
                        .setAttachUrl(attachAddress + newName)
                        .setAttachAddress(uploadPath + newName)
                        .setAttachAlias(newName)
                );
            } catch (IOException e) {
                throw new ServiceException("上传失败!:" + e);
            }
        }
        return attachVOS;
    }
}
