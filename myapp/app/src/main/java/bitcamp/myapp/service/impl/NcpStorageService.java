package bitcamp.myapp.service.impl;

import bitcamp.myapp.service.StorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class NcpStorageService implements StorageService, InitializingBean {
  private static Log log = LogFactory.getLog(NcpStorageService.class);
  final String endPoint;
  final String regionName;
  final String accessKey;
  final String secretKey;
  final AmazonS3 s3;

  public NcpStorageService(@Value("${ncp.ss.endpoint}") String endPoint,
      @Value("${ncp.ss.regionname}") String regionName, @Value("${ncp.accesskey}") String accessKey,
      @Value("${ncp.secretkey}") String secretKey) {
    this.endPoint = endPoint;
    this.regionName = regionName;
    this.accessKey = accessKey;
    this.secretKey = secretKey;

    // S3 client
    s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
        .build();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug(String.format("endPoint: %s", endPoint));
    log.debug(String.format("regionName: %s", regionName));
    log.debug(String.format("accessKey: %s", accessKey));
    log.debug(String.format("secretKey: %s", secretKey));
  }

  @Override
  public String upload(String bucketName, String path, MultipartFile multipartFile)
      throws Exception {
    try (InputStream fileIn = multipartFile.getInputStream()) {
      // upload local file
      String filename = UUID.randomUUID().toString();
      String objectName = path + filename;

      // 서버에 업로드할 파일의 정보를 준비
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(multipartFile.getContentType());

      // 서버 업로드 요청정보 생성
      PutObjectRequest putObjectRequest =
          new PutObjectRequest(
              bucketName,
              objectName,
              fileIn,
              objectMetadata
          ).withCannedAcl(CannedAccessControlList.PublicRead);

      // 서버 업로드 실행
      s3.putObject(putObjectRequest);

      log.debug(String.format("Object %s has been created.\n", objectName));
      return filename;
    }
  }
}
