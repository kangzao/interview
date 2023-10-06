package com.jep.github.interview.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) //基准测试类型：time/ops（每次调用的平均时间）
@OutputTimeUnit(TimeUnit.NANOSECONDS) //基准测试结果的时间类型：微秒
@Warmup(iterations = 5) //预热：5 轮
@Measurement(iterations = 5) //度量：测试5轮
@Fork(3) //Fork出3个线程来测试
@State(Scope.Thread) // 每个测试线程分配1个实例
public class StringAppendJmhTest {

    @Param({
            "1000"})
    private int count; //指定添加元素的不同个数，便于分析结果

    @Setup(Level.Trial) // 初始化方法，在全部Benchmark运行之前进行
    public void init() {

        System.out.println("Start...");
    }

    public static void main(String[] args) throws RunnerException {


        //1、启动基准测试：输出json结果文件（用于查看可视化图）
        Options opt = new OptionsBuilder()
                .include(StringAppendJmhTest.class.getSimpleName()) //要导入的测试类

                .result("/Users/Ian/book/StringAppendJmhTest.json") //输出测试结果的json文件
                .resultFormat(ResultFormatType.JSON)//格式化json文件
                .build();

        //2、执行测试
        new Runner(opt).run();
    }

    @Benchmark
    public void stringAppendTest(Blackhole blackhole) {

        String str = new String();
        for (int i = 0; i < count; i++) {

            str = str + "Justin";
        }
        blackhole.consume(str);
    }

    @Benchmark
    public void stringBufferAppendTest(Blackhole blackhole) {

        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) {

            strBuffer.append("Justin");
        }
        blackhole.consume(strBuffer);
    }

    @Benchmark
    public void stringBuilderAppendTest(Blackhole blackhole) {

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {

            strBuilder.append("Justin");
        }
        blackhole.consume(strBuilder);
    }

    @TearDown(Level.Trial) // 结束方法，在全部Benchmark运行之后进行
    public void clear() {

        System.out.println("End...");
    }

}