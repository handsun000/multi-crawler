package com.ll.multi_crawler.ml.learning;

import opennlp.tools.namefind.*;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class NerTrainer {

    public static void main(String[] args) throws Exception {
        String trainFile = "src/main/resources/ml/train.txt";

        String nerModel = "src/main/resources/ml/ner-model.bin";

        try (ObjectStream<String> lineStream = new PlainTextByLineStream(() -> new FileInputStream(trainFile), StandardCharsets.UTF_8);
             ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

             FileOutputStream modelOut = new FileOutputStream(nerModel)) {

            TrainingParameters params = TrainingParameters.defaultParams();
            params.put(TrainingParameters.ITERATIONS_PARAM, "100");  // 반복횟수
            params.put(TrainingParameters.CUTOFF_PARAM, "1");       // 샘플 최소 빈도

            TokenNameFinderModel model = NameFinderME.train("ko", "custom", sampleStream, params, new TokenNameFinderFactory());
            model.serialize(modelOut);
            System.out.println("NER 모델 학습 완료, ner-model.bin 생성됨.");
        }
    }
}
