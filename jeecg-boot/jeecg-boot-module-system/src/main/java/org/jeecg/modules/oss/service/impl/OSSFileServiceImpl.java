package org.jeecg.modules.oss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.OssUtil;
import org.jeecg.config.oss.OSSManager;
import org.jeecg.config.oss.OSSProperties;
import org.jeecg.modules.oss.entity.OSSFile;
import org.jeecg.modules.oss.mapper.OSSFileMapper;
import org.jeecg.modules.oss.service.IOSSFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("ossFileService")
public class OSSFileServiceImpl extends ServiceImpl<OSSFileMapper, OSSFile> implements IOSSFileService {

    @Autowired
    private OSSManager ossManager;

    @Autowired
    private OSSProperties properties;

    @Override
    public void upload(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String path = OssUtil.splitWeekOfYear() + fileName;
        OSSFile ossFile = new OSSFile();
        ossFile.setFileName(fileName);
        ossFile.setUrl("https://" + properties.getDomainName() + "/" + path);
        this.save(ossFile);
        ossManager.upload(path, multipartFile.getInputStream());
    }

    @Override
    public boolean delete(OSSFile ossFile) {
        try {
            this.removeById(ossFile.getId());
            String path = OssUtil.splitWeekOfYear() + ossFile.getFileName();
            ossManager.delete(path);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}
