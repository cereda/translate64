/**
 * \cond LICENSE
 * ********************************************************************
 * This is a conditional block for preventing the DoxyGen documentation
 * tool to include this license header within the description of each
 * source code file. If you want to include this block, please define
 * the LICENSE parameter into the provided DoxyFile.
 * ********************************************************************
 *
 * Translate64 - Easily convert files to Base64
 * Copyright (c) 2011, Paulo Roberto Massa Cereda
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Neither the name of the project's author nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ********************************************************************
 * End of the LICENSE conditional block
 * ********************************************************************
 * \endcond
 *
 * <b>TranslateUI.java</b>: provides the UI implementation for Translate 64.
 */

// package definition
package net.sf.translate64.ui;

// needed imports
import com.ezware.dialog.task.CommandLink;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import net.sf.translate64.util.FileDrop;
import net.sf.translate64.util.TranslateUtils;

/**
 * Provides the UI implementation for Translate 64.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class TranslateUI extends javax.swing.JFrame {

    /**
     * Constructor method. Creates a new form.
     */
    public TranslateUI() {
        
        // call the inner method for setting components
        initComponents();
        
        // create a new buffered image
        // this image will be both application
        // and taskbar icons
        BufferedImage image = null;
        
        // let's try
        try {
            
            // get image from resource
            image = ImageIO.read(getClass().getResource("/net/sf/translate64/resources/translate.png"));
            
            // set icon
            setIconImage(image);
            
        // something bad happened
        } catch (IOException e) {
            
            // do nothing
        }
        
        // center frame
        setLocationRelativeTo(null);

        // add the drag and drop functionality
        // to the frame panel
        // the parameters are the panel itself, an
        // empty border and a listener
        new FileDrop(dropPanel, new EmptyBorder(0, 0, 0, 0), new FileDrop.Listener() {

            @Override
            public void filesDropped(File[] files) {
                
                // try to handle dropped files
                try {
                    
                    // check if it's only one file
                    if (files.length == 1) {
                        
                        // if so, cconvert it to a base64 string
                        String output = TranslateUtils.convertFile(files[0]);
                        
                        // then set the string to the clipboard
                        TranslateUtils.setClipboardText(output);
                        
                        // display a fancy message
                        TranslateUtils.showInfoMessage(getOwner(), "File converted successfully!", "Done! The file <u>" + files[0].getName() + "</u> was successfully converted\n to a Base64 string and copied to clipboard. Have fun!");
                    }
                    else {
                        // more files were dragged
                        
                        // create a new list of choices
                        List<CommandLink> listCommands = new ArrayList<CommandLink>();
                        
                        // create a cancel option
                        CommandLink clCancel = new CommandLink("Cancel this operation", "Simply cancel this operation. Nothing will be done to any of the dragged files.");
                        
                        // add to the list
                        listCommands.add(clCancel);
                        
                        // create a conversion option
                        CommandLink clFirst = new CommandLink("Convert the first one", "Only the first file in this group, which is <u>" + files[0].getName() + "</u>, will be converted.");
                        
                        // add to the list
                        listCommands.add(clFirst);
                        
                        // create an exit option
                        CommandLink clExit = new CommandLink("Exit the application", "The application will discard the dragged files and will exit.");
                        
                        // add to the list
                        listCommands.add(clExit);
                        
                        // prompt user and ask for a choice
                        int choice = TranslateUtils.showOptions(getOwner(), "Woah, cowboy!", "Due to a design decision, Translate64 can only handle one file per time.\nPlease choose what you want to do.", 0, listCommands);
                        
                        // let's check what the user chose
                        switch (choice) {
                            
                            // cancel
                            case 0:
                                
                                // nothing to do, simply show a fancy message
                                TranslateUtils.showInfoMessage(getOwner(), "Phew!", "Don't worry, no harm done!");
                                
                                // break the switch
                                break;
                                
                            // convert the first file
                            case 1:
                                
                                // convert the file to a base64 string
                                String output = TranslateUtils.convertFile(files[0]);
                                
                                // then set the string to the clipboard
                                TranslateUtils.setClipboardText(output);
                                
                                // display a fancy message
                                TranslateUtils.showInfoMessage(getOwner(), "File converted successfully!", "Done! The file <u>" + files[0].getName() + "</u> was successfully converted\n to a Base64 string and copied to clipboard. Have fun!");
                                
                                // break the switch
                                break;
                                
                            // exit application
                            case 2:
                                
                                // exit
                                System.exit(0);
                                
                                // break the switch
                                // I put this just because of the syntatic
                                // structure
                                break;
                                
                            // user is a cheater
                            default:
                                
                                // then let's diplay a funny message
                                TranslateUtils.showInfoMessage(getOwner(), "So you didn't choose an option...", TranslateUtils.getMessage());
                        }
                        
                    }
                }
                catch (IOException ioe) {
                    
                    // exception, display error
                    TranslateUtils.showException(ioe);
                }
                catch (Exception e) {
                    
                    // exception, display error
                    TranslateUtils.showException(e);
                }
            }
        });

    }

    /** 
     * This method is called from within the constructor to
     * initialize the form. NetBeans is begging me to not edit
     * this code, so I won't.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dropPanel = new javax.swing.JPanel();
        dropImage = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuExitApplication = new javax.swing.JMenuItem();
        menuView = new javax.swing.JMenu();
        menuAlwaysOnTop = new javax.swing.JCheckBoxMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Translate64");
        setResizable(false);

        dropPanel.setPreferredSize(new java.awt.Dimension(124, 124));

        dropImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/translate64/resources/dropfilehere.png"))); // NOI18N

        javax.swing.GroupLayout dropPanelLayout = new javax.swing.GroupLayout(dropPanel);
        dropPanel.setLayout(dropPanelLayout);
        dropPanelLayout.setHorizontalGroup(
            dropPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dropPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dropImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dropPanelLayout.setVerticalGroup(
            dropPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dropPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dropImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuFile.setText("File");

        menuExitApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/translate64/resources/exit.png"))); // NOI18N
        menuExitApplication.setText("Exit application");
        menuExitApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitApplicationActionPerformed(evt);
            }
        });
        menuFile.add(menuExitApplication);

        menu.add(menuFile);

        menuView.setText("View");

        menuAlwaysOnTop.setText("Always on top");
        menuAlwaysOnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAlwaysOnTopActionPerformed(evt);
            }
        });
        menuView.add(menuAlwaysOnTop);

        menu.add(menuView);

        menuHelp.setText("Help");

        menuAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/translate64/resources/about.png"))); // NOI18N
        menuAbout.setText("About...");
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuAbout);

        menu.add(menuHelp);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dropPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dropPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exits the application.
     * @param evt The event.
     */
    private void menuExitApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitApplicationActionPerformed
        
        // exit
        System.exit(0);
    }//GEN-LAST:event_menuExitApplicationActionPerformed

    /**
     * Set the state for this form to be always on top.
     * @param evt The event.
     */
    private void menuAlwaysOnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAlwaysOnTopActionPerformed
        
        // set state
        setAlwaysOnTop(menuAlwaysOnTop.isSelected());
    }//GEN-LAST:event_menuAlwaysOnTopActionPerformed

    /**
     * Show the About info.
     * @param evt The event.
     */
    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        
        // display message
        TranslateUtils.showAbout(getOwner());
    }//GEN-LAST:event_menuAboutActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dropImage;
    private javax.swing.JPanel dropPanel;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JCheckBoxMenuItem menuAlwaysOnTop;
    private javax.swing.JMenuItem menuExitApplication;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuView;
    // End of variables declaration//GEN-END:variables
}
