/**
 * $Revision: $
 * $Date: $
 *
 * Copyright (C) 2007 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the GNU Lesser Public License (LGPL),
 * a copy of which is included in this distribution.
 */

package org.jivesoftware.sparkplugin.preferences;

import org.jivesoftware.sparkplugin.ui.components.JavaMixer;
import net.java.sipmack.softphone.SoftPhoneManager;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.util.GraphicUtils;

import javax.media.CaptureDeviceInfo;
import javax.media.cdm.CaptureDeviceManager;
import javax.media.format.AudioFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Dialog to allow setting of audio device and volume control.
 *
 * @author Jive Software
 */
public class AudioPreferenceDialog extends JPanel {

    private JComboBox audioBox = new JComboBox();
    private JDialog dialog;
    private JButton closeButton;
    private JavaMixer javaMixer = new JavaMixer();

    public AudioPreferenceDialog() {
        setLayout(new GridBagLayout());

        audioBox = new JComboBox();

        final JLabel inputValueLabel = new JLabel("Input Volume:");
        final JLabel outputValueLabel = new JLabel("Output Volume:");

        add(inputValueLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
        add(javaMixer.getPrefferedInputVolume(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

        add(outputValueLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
        add(javaMixer.getPrefferedMasterVolume(), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

        closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                SipPreference pref = SoftPhoneManager.getInstance().getPreference();
                SipPreferences preferences = (SipPreferences)pref.getData();
                preferences.setAudioDevice((String)audioBox.getSelectedItem());
                pref.saveSipFile();
                dialog.dispose();
            }
        });
        add(closeButton, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));

        final Vector audioDevices = CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR, 44100, 16, 1));
        final int dev = audioDevices != null ? audioDevices.size() : 0;
        for (int i = 0; i < dev; i++) {
            CaptureDeviceInfo audioDevice = (CaptureDeviceInfo)audioDevices.get(i);
            audioBox.addItem(audioDevice.getName());
        }


        SipPreference pref = SoftPhoneManager.getInstance().getPreference();
        SipPreferences preferences = (SipPreferences)pref.getData();
        String selectedDevice = preferences.getAudioDevice();
        if (selectedDevice != null) {
            audioBox.setSelectedItem(selectedDevice);
        }


    }

    public void invoke() {
        if (dialog == null) {
            dialog = new JDialog(SparkManager.getMainWindow(), "Audio Preferences");
            dialog.setLayout(new BorderLayout());
            dialog.add(this, BorderLayout.CENTER);
            dialog.pack();

            GraphicUtils.centerWindowOnScreen(dialog);
        }

        dialog.setVisible(true);
    }

}