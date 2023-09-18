package com.jep.github.interview.performance.exception;

import com.jep.github.interview.performance.StringAppendJmhTest;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Fork(1)  //指定 JMH 必须生成新进程以运行基准测试的次数。我们将它的值设置为1，只生成一个进程，避免等待太久才能看到结果
@Warmup(iterations = 2) //预热参数。iterations元素为 2 表示在计算结果时忽略前两次运行
@Measurement(iterations = 10) //测量参数。迭代值为 10 表示 JMH 将每个方法执行 10次
@BenchmarkMode(Mode.AverageTime) //这是 JHM 收集执行结果的方式。AverageTime值要求 JMH 计算方法完成其操作所需的平均时间
@OutputTimeUnit(TimeUnit.MILLISECONDS) //表示输出时间单位，本例为毫秒
public class ExceptionBenchmark {
    private static final int LIMIT = 10_000;

    //    blackhole参数是Blackhole类型的一个实例。Blackhole是JMH提供的一个用于消耗方法返回值的工具类，
    //    其作用是防止JIT编译器对方法返回值的优化，从而更准确地测量方法的性能。
    @Benchmark
    public void doNotThrowException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Object());
        }
    }

    @Benchmark
    public void throwAndCatchException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }

    @Benchmark
    public void createExceptionWithoutThrowingIt(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Exception());
        }
    }

    @Benchmark
//    -XX:-StackTraceInThrowable是一个 JVM 选项，作用是防止堆栈跟踪被添加到异常对象中。
//    使用 -XX:-StackTraceInThrowable参数后，Throwable.getStackTrace()将返回一个空数组。默认情况下，
//    创建Java异常对象时，JVM 会遍历当前线程的堆栈，将VM方法调用栈保存到异常对象Throwable中。
    @Fork(value = 1, jvmArgs = "-XX:-StackTraceInThrowable")
    public void throwExceptionWithoutAddingStackTrace(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }

    @Benchmark
    public void throwExceptionAndUnwindStackTrace(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e.getStackTrace());
            }
        }
    }

    public static void main(String[] args) throws RunnerException {


        //1、启动基准测试：输出json结果文件（用于查看可视化图）
        Options opt = new OptionsBuilder()
                .include(ExceptionBenchmark.class.getSimpleName()) //要导入的测试类

                .result("/Users/Ian/book/ExceptionBenchmark.json") //输出测试结果的json文件
                .resultFormat(ResultFormatType.JSON)//格式化json文件
                .build();

        //2、执行测试
        new Runner(opt).run();
    }
}