import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.transfer.s3.FileUpload;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.nio.file.Paths;

public class UploadObject {

    public static void main( String[] args ) {
        final String usage = """

                Usage:
                  <bucketName> <objectKey> <objectPath>\s

                Where:
                  bucketName - The Amazon S3 bucket to upload an object into.
                  objectKey - The object to upload (for example, book.pdf).
                  objectPath - The path where the file is located (for example, C:/AWS/book2.pdf).\s

                """;

        if (args.length != 3) {
            System.out.println(usage);
            System.exit(1);
        }

        long mb = 1024;
        String bucketName = args[0];
        String objectKey = args[1];
        String objectPath = args[2];

        System.out.println("Putting an object into bucket '" + bucketName +
                "' using the S3TransferManager...\s");
        // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/setup.html#setup-credentials
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.EU_WEST_1;
        S3TransferManager transferManager = S3TransferManager.builder()
                .s3ClientConfiguration(cfg -> cfg.region(region)
                        .credentialsProvider(credentialsProvider)
                        .targetThroughputInGbps(20.0)
                        .minimumPartSizeInBytes(10 * mb))
                .build();

        uploadObjectTM(transferManager, bucketName, objectKey, objectPath);
        System.out.println("Object was successfully uploaded using the Transfer Manager.");
        transferManager.close();
    }

    private static void uploadObjectTM(S3TransferManager transferManager, String bucketName,
                                       String objectKey, String objectPath) {
        FileUpload fileUpload = transferManager.uploadFile(u -> u.source(Paths.get(objectPath))
                .putObjectRequest(p -> p.bucket(bucketName).key(objectKey)));
        fileUpload.completionFuture().join();
    }
}
