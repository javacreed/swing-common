package com.javacreed.api.swing.common.combobox;

import org.junit.Assert;
import org.junit.Test;

public class StringSearchUtils_Test {

  @Test
  public void test() {
    final String text = "Għawdex, il-gżira oħt Malta";
    Assert.assertEquals(1, StringSearchUtils.indexOf(text, "h"));
    Assert.assertEquals(19, StringSearchUtils.indexOf(text, "h", 2));
    Assert.assertEquals(22, StringSearchUtils.indexOf(text, "malta"));
    Assert.assertEquals(22, StringSearchUtils.indexOf(text, "malta", 22));
  }
}
