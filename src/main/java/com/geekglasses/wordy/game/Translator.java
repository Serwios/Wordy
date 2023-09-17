package com.geekglasses.wordy.game;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class Translator {
    public static String translate(String word) {
        AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return "";
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return "";
                    }
                };
            }

            @Override
            public void refresh() {

            }
        };

        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(credentialsProvider)
                .withRegion("eu-central-1")
                .build();

        String ENGLISH = "en";
        String UKRAINIAN = "uk";
        TranslateTextRequest request = new TranslateTextRequest()
                .withText(word)
                .withSourceLanguageCode(ENGLISH)
                .withTargetLanguageCode(UKRAINIAN);
        TranslateTextResult result  = translate.translateText(request);

        return result.getTranslatedText();
    }
}
