package com.jianglibo.nutchbuilder.hbaserest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.hbaserest.HbaseAllTables.HbaseTableName;

public class TestHbaseRest extends Tbase {

	@Autowired
	private RestTemplate hbaseRestTemplate;
	
	@Autowired
	private CommonHbaseInformationRetriver chir;
	
	private String ttableName = "my_test_table";
	
	@Before
	public void b() {
		chir.deleteTable(ttableName);
	}
	
	@Test
	public void tHbaseTables() {
		List<HbaseTableName> hstv = chir.getAllNoneSystemTable();
		hstv.stream().filter(t -> "SYSTEM.CATALOG".equals(t.getName())).findFirst().get();
		hstv.stream().filter(t -> "SYSTEM.FUNCTION".equals(t.getName())).findFirst().get();
		hstv.stream().filter(t -> "SYSTEM.SEQUENCE".equals(t.getName())).findFirst().get();
		HbaseAllTables hrrr = hbaseRestTemplate.getForObject(applicationConfig.getHbaseRestPrefix(), HbaseAllTables.class);
		hstv = hrrr.getTable();
		hstv.stream().filter(t -> "SYSTEM.CATALOG".equals(t.getName())).findFirst().get();
		hstv.stream().filter(t -> "SYSTEM.FUNCTION".equals(t.getName())).findFirst().get();
		hstv.stream().filter(t -> "SYSTEM.SEQUENCE".equals(t.getName())).findFirst().get();
		hstv.forEach(System.out::println);
		assertTrue(true);
	}
	
	@Test
	public void tCreateTable() {
		HttpStatus hs = chir.createTable(ttableName, "{\"name\":\"" + ttableName + "\",\"ColumnSchema\":[{\"name\":\"0\",\"BLOOMFILTER\":\"ROW\",\"VERSIONS\":\"1\",\"IN_MEMORY\":\"false\",\"KEEP_DELETED_CELLS\":\"FALSE\",\"DATA_BLOCK_ENCODING\":\"FAST_DIFF\",\"TTL\":\"2147483647\",\"COMPRESSION\":\"NONE\",\"MIN_VERSIONS\":\"0\",\"BLOCKCACHE\":\"true\",\"BLOCKSIZE\":\"65536\",\"REPLICATION_SCOPE\":\"0\"}],\"IS_META\":\"false\",\"coprocessor$5\":\"|org.apache.phoenix.hbase.index.Indexer|805306366|org.apache.hadoop.hbase.index.codec.class=org.apache.phoenix.index.PhoenixIndexCodec,index.builder=org.apache.phoenix.index.PhoenixIndexBuilder\",\"coprocessor$3\":\"|org.apache.phoenix.coprocessor.GroupedAggregateRegionObserver|805306366|\",\"coprocessor$4\":\"|org.apache.phoenix.coprocessor.ServerCachingEndpointImpl|805306366|\",\"coprocessor$1\":\"|org.apache.phoenix.coprocessor.ScanRegionObserver|805306366|\",\"coprocessor$2\":\"|org.apache.phoenix.coprocessor.UngroupedAggregateRegionObserver|805306366|\"}");
		assertThat("is 201", hs.value(), equalTo(201));
		HbaseTableSchema hts = chir.getTableSchema(ttableName);
		assertNotNull(hts);
		
		hs = chir.createTable(ttableName, "{\"name\":\"" + ttableName + "\",\"ColumnSchema\":[{\"name\":\"0\",\"BLOOMFILTER\":\"ROW\",\"VERSIONS\":\"1\",\"IN_MEMORY\":\"false\",\"KEEP_DELETED_CELLS\":\"FALSE\",\"DATA_BLOCK_ENCODING\":\"FAST_DIFF\",\"TTL\":\"2147483647\",\"COMPRESSION\":\"NONE\",\"MIN_VERSIONS\":\"0\",\"BLOCKCACHE\":\"true\",\"BLOCKSIZE\":\"65536\",\"REPLICATION_SCOPE\":\"0\"}],\"IS_META\":\"false\",\"coprocessor$5\":\"|org.apache.phoenix.hbase.index.Indexer|805306366|org.apache.hadoop.hbase.index.codec.class=org.apache.phoenix.index.PhoenixIndexCodec,index.builder=org.apache.phoenix.index.PhoenixIndexBuilder\",\"coprocessor$3\":\"|org.apache.phoenix.coprocessor.GroupedAggregateRegionObserver|805306366|\",\"coprocessor$4\":\"|org.apache.phoenix.coprocessor.ServerCachingEndpointImpl|805306366|\",\"coprocessor$1\":\"|org.apache.phoenix.coprocessor.ScanRegionObserver|805306366|\",\"coprocessor$2\":\"|org.apache.phoenix.coprocessor.UngroupedAggregateRegionObserver|805306366|\"}");
		assertThat("is 200", hs.value(), equalTo(200));
		
		assertTrue("will success", chir.deleteTable(ttableName));
		assertFalse("will fail, because of 404 not found.", chir.deleteTable(ttableName));
		HbaseTableSchema mso = chir.getTableSchema(ttableName);
		assertNull("404 not found", mso);
	}
}
