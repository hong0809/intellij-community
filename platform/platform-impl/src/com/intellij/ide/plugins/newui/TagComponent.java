// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.ide.plugins.newui;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.application.impl.ApplicationInfoImpl;
import com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.PluginAdvertiserService;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @author Alexander Lobas
 */
public final class TagComponent extends LinkComponent {
  private static final Color BACKGROUND = JBColor.namedColor("Plugins.tagBackground", new JBColor(0xEAEAEC, 0x4D4D4D));
  private static final Color EAP_BACKGROUND = JBColor.namedColor("Plugins.eapTagBackground", new JBColor(0xF2D2CF, 0xF2D2CF));
  @SuppressWarnings("UnregisteredNamedColor")
  private static final Color PAID_BACKGROUND = JBColor.namedColor("Plugins.paidTagBackground", new JBColor(0xD8EDF8, 0x3E505C));
  @SuppressWarnings("UnregisteredNamedColor")
  private static final Color TRIAL_BACKGROUND = JBColor.namedColor("Plugins.trialTagBackground", new JBColor(0xDBE8DD, 0x345574E));
  private static final Color FOREGROUND = JBColor.namedColor("Plugins.tagForeground", new JBColor(0x787878, 0x999999));

  @SuppressWarnings("UnregisteredNamedColor")
  private static final Color ULTIMATE_FOREGROUND = JBColor.namedColor("Plugins.ultimateTagForeground", new JBColor(0x222222, 0xFFFFFF));

  private Color myColor;

  public TagComponent() {
    setForeground(FOREGROUND);
    setPaintUnderline(false);
    setOpaque(false);
    setBorder(JBUI.Borders.empty(1, 8));
  }

  public TagComponent(@NotNull @Nls String name) {
    this();
    setText(name);
  }

  @Override
  public void setText(@NotNull @Nls String name) {
    String tooltip = null;
    myColor = BACKGROUND;

    if (Tags.EAP.name().equals(name)) {
      myColor = EAP_BACKGROUND;
      tooltip = IdeBundle.message("tooltip.eap.plugin.version");
    }
    else if (Tags.Trial.name().equals(name) || Tags.Purchased.name().equals(name)) {
      myColor = TRIAL_BACKGROUND;
    }
    else if (Tags.Paid.name().equals(name) || Tags.Freemium.name().equals(name)) {
      myColor = PAID_BACKGROUND;
      tooltip = IdeBundle.message("tooltip.paid.plugin");
    }
    else if (Tags.Pro.name().equals(name) || Tags.Ultimate.name().equals(name)) {
      myColor = PAID_BACKGROUND;
      setForeground(ULTIMATE_FOREGROUND);
      var thisProductCode = ApplicationInfoImpl.getShadowInstanceImpl().getBuild().getProductCode();
      var suggestedIdeCode = PluginAdvertiserService.getSuggestedCommercialIdeCode(thisProductCode);
      if (suggestedIdeCode != null) {
        var ide = PluginAdvertiserService.getIde(suggestedIdeCode);
        if (ide != null) {
          tooltip = IdeBundle.message("tooltip.ultimate.plugin", ide.getName());
        }
      }
    }

    super.setText(name);
    setToolTipText(tooltip);
  }

  @Override
  protected void paintComponent(Graphics g) {
    //noinspection UseJBColor
    g.setColor(myUnderline ? new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), 178) : myColor);
    g.fillRect(0, 0, getWidth(), getHeight());
    super.paintComponent(g);
  }

  @Override
  protected boolean isInClickableArea(Point pt) {
    return true;
  }
}