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
 * <b>TranslateUtils.java</b>: provides static helper methods to Translate 64.
 * This class does not need to be instantiated.
 */

// package definition
package net.sf.translate64.util;

// needed imports
import com.ezware.dialog.task.CommandLink;
import com.ezware.dialog.task.TaskDialogs;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import javax.swing.UIManager;
import org.apache.commons.codec.binary.Base64;

/**
 * Provides static helper methods to Translate 64. This class does not need
 * to be instantiated.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class TranslateUtils {

    /**
     * Set the native look and feel.
     */
    public static void setNativeLookAndFeel() {
        
        // let's try
        try {
            
            // set the default look and feel as the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            
            // something happened, but do nothing
        }
    }
    
    /**
     * Display an info message.
     * @param window The window.
     * @param title The message title.
     * @param text The message text.
     */
    public static void showInfoMessage(Window window, String title, String text) {
        
        // call the proper method
        TaskDialogs.inform(window, title, text);
    }
    
    /**
     * Display a warning message.
     * @param window The window.
     * @param title The message title.
     * @param text The message text.
     * @return <code>true</code> if user clicked in the Yes button, or
     * <code>false</code> otherwise.
     */
    public static boolean showWarningMessage(Window window, String title, String text) {
        
        // call the proper method
        return TaskDialogs.isConfirmed(window, title, text);
    }
    
    /**
     * Display an error message.
     * @param window The window.
     * @param title The message title.
     * @param text The message text.
     */
    public static void showErrorMessage(Window window, String title, String text) {
        
        // call the proper method
        TaskDialogs.error(window, title, text);
    }
    
    /**
     * Display a list of option for the user to choose.
     * @param window The window.
     * @param title The message title.
     * @param text The message text.
     * @param choice The default choice.
     * @param choices A list containing all the choices.
     * @return The index referring to the chosen option.
     */
    public static int showOptions(Window window, String title, String text, int choice, List<CommandLink> choices) {
        
        // call the proper method
        return TaskDialogs.choice(window, title, text, choice, choices);
    }
    
    /**
     * Display an exception message.
     * @param e The exception.
     */
    public static void showException(Throwable e) {
        
        // call the proper method
        TaskDialogs.showException(e);
    }
    
    /**
     *  Returns the contents of the file in a byte array.
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        
        // create an input stream
        InputStream istream = new FileInputStream(file);

        // get the size
        long length = file.length();

        // check the length
        if (length > Integer.MAX_VALUE) {
            
            // file is too large
        }

        // create the byte array
        byte[] bytes = new byte[(int)length];

        // read in the bytes
        int offset = 0;
        
        // counter
        int numRead = 0;
        
        // do the trick
        while (offset < bytes.length
               && (numRead=istream.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // ensure all the bytes have been read in
        if (offset < bytes.length) {
            
            // throw an exception
            throw new IOException("Could not completely read file "+file.getName());
        }

        // close the input stream
        istream.close();
        
        // return bytes
        return bytes;
    }
    
    /**
     * Converts the file to a Base64 string.
     * @param file The file.
     * @return The string.
     * @throws IOException An IO error with the file. 
     */
    public static String convertFile(File file) throws IOException {
        
        // call the method from Apache Commons Codec
        String output = Base64.encodeBase64String(getBytesFromFile(file));
        
        // return the string
        return output;
    }
    
    /*
     * Set the content of the clipboard.
     */
    public static void setClipboardText(String text) {
        
        // create a new clipboard instance
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        // set the contents
        clipboard.setContents(new StringSelection(text), null);
    }
    
    /**
     * Gets a random funny message.
     * @return The random message.
     */
    public static String getMessage() {
        
        // the messages
        String[] messages = {
            "Every time you do this, a kitten dies.",
            "Your TV is lonely right now.",
            "Heeeeeere fishy, fishy, fishy!",
            "Don't feel sad, don't feel glue, Einstein was ugly too.",
            "Love me or leave me. Hey, where is everybody going?",
            "Roses are red,\nviolets are blue,\n most poems ryhm,\nbut this one doesn't.",
            "Heeere's Johnny!",
            "There are three kinds of people: those who can count and those who can't.",
            "The beatings will continue until morale improves.",
            "Copywight 1994 Elmer Fudd. All wights wesewved.",
            "2 + 2 = 5 for extremely large values of 2.",
            "This is not a bug, is a random feature.",
            "I took an IQ test and the results were negative."
        };
        
        // create a random generator
        Random randomGenerator = new Random();
        
        // return a random message
        return messages[randomGenerator.nextInt(messages.length)];
    }
    
    /**
     * Display the About message.
     * @param window The window.
     */
    public static void showAbout(Window window) {
        
        // define the Copyright symbol
        final String COPYRIGHT  = "\u00a9";
        
        // call the proper method
        TaskDialogs.inform(window, "Translate 64", "<i>Version 1.0</i>\n\nCopyright " + COPYRIGHT + " 2011, Paulo Roberto Massa Cereda\nAll rights reserved.\n\nThis application is licensed under the <u>New BSD License</u>. I want to call your attention\nto the fact that the <i>New BSD License</i> has been verified as a <i>GPL-compatible free\nsoftware license</i> by the Free Software Foundation, and has been vetted as an <i>open\nsource license</i> by the Open Source Initiative.");
    }
    
}
