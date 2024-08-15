/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.hyansts;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
public class StringTemplateFormatterBenchmark {

	private StringTemplateFormatter formatter;
	private String[] values;
	private String template1;
	private String template2;
	private String template3;

	@Setup
	public void setup() {
		// Generates the templates
		this.formatter = new StringTemplateFormatter();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		this.values = new String[10000];
		for (int i = 0; i < 10000; i++) {
			sb.append("You have ${").append(i).append("} new messages.\n");
			sb2.append("You have %s new messages.\n");
			sb3.append("You have {").append(i).append("} new messages.\n");
			this.formatter.put(String.valueOf(i), i);
			this.values[i] = String.valueOf(i);
		}

		this.template1 = sb.toString();
		this.template2 = sb2.toString();
		this.template3 = sb3.toString();

		// Guarantee that the templates are equal before benchmarking
		String f1 = formatter.format(template1);
		String f2 = String.format(template2, (Object[]) values);
		String f3 = MessageFormat.format(template3, (Object[]) values);
		if (!f1.equals(f2) || !f1.equals(f3)) {
			throw new RuntimeException("Templates are not equal");
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 1, time = 3)
	@Measurement(iterations = 5, time = 3)
	@Fork(1)
	public String benchmarkStringTemplateFormat() {
		return this.formatter.format(template1);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 1, time = 3)
	@Measurement(iterations = 5, time = 3)
	@Fork(1)
	public String benchmarkStringFormat() {
		return String.format(template2, (Object[]) values);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 1, time = 3)
	@Measurement(iterations = 5, time = 3)
	@Fork(1)
	public String benchmarkMessageFormat() {
		return MessageFormat.format(template3, (Object[]) values);
	}
}
