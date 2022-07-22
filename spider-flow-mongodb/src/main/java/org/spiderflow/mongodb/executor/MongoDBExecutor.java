package org.spiderflow.mongodb.executor;

import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.spiderflow.context.SpiderContext;
import org.spiderflow.executor.ShapeExecutor;
import org.spiderflow.model.Shape;
import org.spiderflow.model.SpiderNode;
import org.spiderflow.mongodb.utils.MongoDBUtils;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
@Component
public class MongoDBExecutor implements ShapeExecutor{

	public static final String MONGODB_HOST = "host";
	public static final String MONGODB_POST = "port";
	public static final String MONGODB_DATABASE = "database";
	public static final String MONGODB_TABLE = "table";
	public static final String MONGODB_ADMIN_NAME = "adminName";
	public static final String MONGODB_PASSOWRD = "password";
	
	public static final String MONGODB_CONTEXT_KEY = "$mongodb_";
	
	
	@Override
	public void execute(SpiderNode node, SpiderContext context, Map<String, Object> variables) {
		String host = node.getStringJsonValue(MONGODB_HOST);
		int port = NumberUtils.toInt(node.getStringJsonValue(MONGODB_POST),27017);
		String database = node.getStringJsonValue(MONGODB_DATABASE);
		String table = node.getStringJsonValue(MONGODB_TABLE);
		String adminName = node.getStringJsonValue(MONGODB_ADMIN_NAME);
		String password = node.getStringJsonValue(MONGODB_PASSOWRD);
		DBCollection DbCollection = MongoDBUtils.createMongoDBTemplate(host, port,database,table,adminName,password);
		context.put(MONGODB_CONTEXT_KEY + node.getNodeId(), DbCollection);
	}
	
	@Override
	public Shape shape() {
		Shape shape = new Shape();
		shape.setImage("data:image/png;base64,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");
		shape.setLabel("mongodb");
		shape.setName("mongodb");
		shape.setTitle("mongodb");
		return shape;
	};
	@Override
	public String supportShape() {
		// TODO Auto-generated method stub
		return "mongodb";
	}
}
