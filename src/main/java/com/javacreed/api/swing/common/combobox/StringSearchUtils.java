package com.javacreed.api.swing.common.combobox;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;

public class StringSearchUtils {

  public static int indexOf(final Collator collator, final String source, final String pattern) {
    return StringSearchUtils.indexOf(collator, source, pattern, 0);
  }

  public static int indexOf(final Collator collator, final String source, final String pattern,
      final int startFromIndex) {
    for (int i = startFromIndex, length = pattern.length(), limit = source.length() - length; i <= limit; i++) {
      if (collator.equals(source.substring(i, i + length), pattern)) {
        return i;
      }
    }

    return -1;
  }

  public static int indexOf(final String source, final String pattern) {
    return StringSearchUtils.indexOf(StringSearchUtils.malteseCollator(), source, pattern, 0);
  }

  public static int indexOf(final String source, final String pattern, final int startFromIndex) {
    return StringSearchUtils.indexOf(StringSearchUtils.malteseCollator(), source, pattern, startFromIndex);
  }

  public static Collator malteseCollator() {
    final String simpleRule = "< a,A,għ,Għ,GĦ,gh,Gh,GH < b,B < c,C,ċ,Ċ < d,D < e,E < f,F " + //
        "< g,G,ġ,Ġ < h,ħ,H,Ħ < i,I,ie,IE,Ie < j,J < k,K < l,L " + //
        "< m,M < n,N < o,O < p,P < q,Q < r,R " + //
        "< s,S < t,T < u,U < v,V < w,W < x,X " + //
        "< y,Y < z,Z,ż,Ż";

    final RuleBasedCollator collator;
    try {
      collator = new RuleBasedCollator(simpleRule);
    } catch (final ParseException e) {
      throw new RuntimeException("Failed to create the rule based collator", e);
    }
    collator.setStrength(Collator.SECONDARY);
    return collator;
  }
}
