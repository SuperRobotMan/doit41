package cn.doit.day02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.tool.LoadIncrementalHFiles;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BulkLoadDemo {
    public static class BulkLoadMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue>{
        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue>.Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split(",");
            //1,zss,18,female
            context.write(new ImmutableBytesWritable(split[0].getBytes(StandardCharsets.UTF_8)),new KeyValue(split[0].getBytes(StandardCharsets.UTF_8), "f1".getBytes(StandardCharsets.UTF_8), "name".getBytes(StandardCharsets.UTF_8), split[1].getBytes(StandardCharsets.UTF_8)));
            context.write(new ImmutableBytesWritable(split[0].getBytes(StandardCharsets.UTF_8)),new KeyValue(split[0].getBytes(StandardCharsets.UTF_8), "f1".getBytes(StandardCharsets.UTF_8), "age".getBytes(StandardCharsets.UTF_8), split[2].getBytes(StandardCharsets.UTF_8)));
            context.write(new ImmutableBytesWritable(split[0].getBytes(StandardCharsets.UTF_8)),new KeyValue(split[0].getBytes(StandardCharsets.UTF_8), "f2".getBytes(StandardCharsets.UTF_8), "gender".getBytes(StandardCharsets.UTF_8), split[3].getBytes(StandardCharsets.UTF_8)));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        System.setProperty("HADOOP_USER_NAME","root");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","linux01:2181");

        Job job = Job.getInstance(conf);

        //设置主类的类名
        job.setJarByClass(BulkLoadDemo.class);

        //设置map类的class
        job.setMapperClass(BulkLoadMapper.class);

        //设置map类输出的key和value的class
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(KeyValue.class);

        //设置reducetask的任务个数
        job.setNumReduceTasks(0);

        /**
         * 对hfile进行设置
         */
        Connection conn = ConnectionFactory.createConnection(conf);
        TableName tableName = TableName.valueOf("doit:userinfo");
        Admin admin = conn.getAdmin();
        Table table = conn.getTable(tableName);
        RegionLocator regionLocator = conn.getRegionLocator(tableName);
        //给hfile设置一些必要的属性，具体有表，还有region的加载器
        HFileOutputFormat2.configureIncrementalLoad(job,table,regionLocator);

        //设置整体文件的输出格式 Hfile
        job.setOutputFormatClass(HFileOutputFormat2.class);

        //文件输入输出的路径
        FileInputFormat.setInputPaths(job,new Path("data/user_info.txt"));
        FileOutputFormat.setOutputPath(job,new Path("hdfs://linux01:8020/output/"));

        //启动入口
        boolean b = job.waitForCompletion(true);

        if (b){
            LoadIncrementalHFiles loadIncrementalHFiles = new LoadIncrementalHFiles(conf);
            /**
             * 1.hfile现在的存储路径
             * 2.admin对象
             * 3.表对象
             * 4.region的加载器
             */
            loadIncrementalHFiles.doBulkLoad(new Path("hdfs://linux01:8020/output/"),admin,table,regionLocator);
        }


    }
}
