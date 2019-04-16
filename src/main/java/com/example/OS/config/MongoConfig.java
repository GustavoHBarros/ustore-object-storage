package com.example.OS.config;

import com.mongodb.MongoClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class MongoConfig extends AbstractMongoConfiguration {

  @Value("${pring.data.mongodb.host}")
  private String host;
  @Value("${pring.data.mongodb.port}")
  private int port;
  @Value("${pring.data.mongodb.database}")
  private String database;

  @Bean
  public GridFsTemplate gridFsTemplate() throws Exception {
      return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
  }

  @Override
  public MongoClient mongoClient() {
      return new MongoClient(host, port);
  }

  @Override
  protected String getDatabaseName() {
      return database;
  }
}
