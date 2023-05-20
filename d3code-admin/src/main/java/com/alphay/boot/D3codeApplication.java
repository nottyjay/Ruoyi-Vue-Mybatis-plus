package com.alphay.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 *
 * @author d3code
 */
@SpringBootApplication(scanBasePackages = {"${d3code.auto-scan-package}"})
public class D3codeApplication {
  public static void main(String[] args) {
    // System.setProperty("spring.devtools.restart.enabled", "false");
    SpringApplication.run(D3codeApplication.class, args);
    System.out.println(
        "(♥◠‿◠)ﾉﾞ  D3Code启动成功   ლ(´ڡ`ლ)ﾞ  \n"
            + "....,OOOOOOOOOOOO\\............./@@@@@@@@@@O`...,/@@@@@@@@@@@`...OOOOOOOOOOOOO`...oooooooooooooo`...\n"
            + "....OOOOOOOOOOOOOOO\\........./@@@@@@@@@@@@@@^,@@@@@@@@@@@@@@@@`=OOOOOOOOOOOOOOO`=oooooooooooooo^...\n"
            + "....OOOO^.......\\OOOO......./@@@@`.......[O`.@@@@@`......,\\@@@@/OOO^.......\\OOOO/ooo^..............\n"
            + "....OOOO^........OOOO.......@@@@^...........=@@@@..........@@@@@OOO^........OOOOOOOOO@@@@@@@@`.....\n"
            + "....OOOO^........OOOO.......@@@@^...........=@@@@..........@@@@@OOO^........OOOOOOOOO@@@@@@@@@.....\n"
            + "....OOOO^.......,OOOO.......@@@@^...........=@@@@`.........@@@@@OOO^........OOOOOOOO/[[[[[[[`......\n"
            + "....OOOO\\]]]]]]/OOOO^.......,OOOo\\]]]]]]/ooo^\\@@@@@@OOOOO@@@@@@=OOOO]]]]]]/OOOOO=ooo\\]]]]]]]]]`....\n"
            + "....OOOOOOOOOOOOOOO`.........,oooooooooooooo`.=@@@@@@@@@@@@@@/.=OOOOOOOOOOOOOO/.=oooooooooooooo^...\n"
            + ".....[[[[[[[[[[[`...............,[[[[[[[[`.......[[\\OOOO/[[.....,[[[[[[[[[[[.....,[[[[[[[[[[[[`....\n");
  }
}
