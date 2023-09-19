package com.jep.github.interview.performance;

import com.google.common.base.Splitter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author enping.jep
 * @date 2023/09/18 15:10
 **/

@BenchmarkMode(Mode.SingleShotTime) //检测一次调用
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//iterations：测量迭代次数，time每次迭代用时，batchSize：相当于给函数加了一个for循环（整个for循环完成的时间要>time），整个for循环算一个operation
@Measurement(batchSize = 100000, iterations = 10)
@Warmup(batchSize = 100000, iterations = 10)
@State(Scope.Thread)
public class StringSplitJmhTest {
    protected String emptyString = " ";
    protected String longString = "Hello, I am a bit longer than other Strings";
    List stringTokenizer = new ArrayList<>();

    List stringSplit = new ArrayList<>();

    protected Pattern spacePattern = Pattern.compile(emptyString);

    @Setup(Level.Trial) // 初始化方法，在全部Benchmark运行之前进行
    public void init() {
        System.out.println("Start...");
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(StringSplitJmhTest.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }


    @Benchmark
    public String[] benchmarkStringSplit() {
        return longString.split(emptyString);
    }

    @Benchmark
    public List<String> benchmarkGuavaSplitter() {
        return Splitter.on(" ").trimResults()
                .omitEmptyStrings()
                .splitToList(longString);
    }


    @Benchmark
    public List benchmarkStringTokenizer() {
        StringTokenizer st = new StringTokenizer(longString);
        while (st.hasMoreTokens()) {
            stringTokenizer.add(st.nextToken());
        }
        return stringTokenizer;
    }


    @Benchmark
    public List benchmarkStringIndexOf() {
        int pos = 0, end;
        while ((end = longString.indexOf(' ', pos)) >= 0) {
            stringSplit.add(longString.substring(pos, end));
            pos = end + 1;
        }
        stringSplit.add(longString.substring(pos));
        return stringSplit;
    }

    @Benchmark
    public String[] benchmarkStringSplitPattern() {
        return spacePattern.split(longString, 0);
    }

    @TearDown(Level.Trial) // 结束方法，在全部Benchmark运行之后进行
    public void clear() {
        System.out.println("End...");
    }
}

