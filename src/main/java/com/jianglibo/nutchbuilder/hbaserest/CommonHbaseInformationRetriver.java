package com.jianglibo.nutchbuilder.hbaserest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.hbaserest.HbaseAllTables.HbaseTableName;

@Component
public class CommonHbaseInformationRetriver {
	
	private static Logger log = LoggerFactory.getLogger(CommonHbaseInformationRetriver.class);

	@Autowired
	private RestTemplate hbaseRestTemplate;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	public List<HbaseTableName> getAllNoneSystemTable() {
		ResponseEntity<HbaseAllTables> rs = hbaseRestTemplate.getForEntity(applicationConfig.getHbaseRestPrefix(), HbaseAllTables.class);
		return rs.getBody().getTable();
	}
	
	private String getTableSchemaUrl(String table) {
		return applicationConfig.getHbaseRestPrefix() + "/" + table + "/schema"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HbaseTableSchema getTableSchema(String table) {
		try {
			ResponseEntity<Map> rs = hbaseRestTemplate.getForEntity(getTableSchemaUrl(table), Map.class);
			return new HbaseTableSchema(rs.getBody());
		} catch (RestClientException e) {
			log.info("{} not found. error message is: {}", table, e.getMessage());
			return null;
		}
	}
	
	public boolean deleteTable(String table) {
		try {
			hbaseRestTemplate.delete(getTableSchemaUrl(table));
			return true;
		} catch (RestClientException e) {
			log.info("{} not found. error message is: {}", table, e.getMessage());
			return false;
		}
	}
	
	public HttpStatus createTable(String table, String body) {
		ResponseEntity<String> rs = hbaseRestTemplate.postForEntity(getTableSchemaUrl(table), body, String.class);
		return rs.getStatusCode();
	}
	
//	Invoke-WebRequest -Uri http://s110.host.name:8080/US_POPULATION1/schema -Headers @{Accept="application/json"} -ContentType "application/json" -Method Post -Body '{"name":"US_POPULATION1","ColumnSchema":[{"name":"0","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"FAST_DIFF","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"}],"IS_META":"false","coprocessor$5":"|org.apache.phoenix.hbase.index.Indexer|805306366|org.apache.hadoop.hbase.index.codec.class=org.apache.phoenix.index.PhoenixIndexCodec,index.builder=org.apache.phoenix.index.PhoenixIndexBuilder","coprocessor$3":"|org.apache.phoenix.coprocessor.GroupedAggregateRegionObserver|805306366|","coprocessor$4":"|org.apache.phoenix.coprocessor.ServerCachingEndpointImpl|805306366|","coprocessor$1":"|org.apache.phoenix.coprocessor.ScanRegionObserver|805306366|","coprocessor$2":"|org.apache.phoenix.coprocessor.UngroupedAggregateRegionObserver|805306366|"}'
	
}
