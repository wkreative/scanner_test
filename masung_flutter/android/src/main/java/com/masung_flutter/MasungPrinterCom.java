package com.masung_flutter;

import android.content.Context;
import com.masung_flutter.msprintsdk.PrintCmd;
import com.masung_flutter.msprintsdk.PrinterService;



/**
 * MasungPrinterCom is a class that provides an interface for interacting with a Masung printer.
 * It provides methods for initializing the printer, printing text and images, cutting paper, and getting the printer status.
 */
public class MasungPrinterCom {

    PrinterService printerService;

    /**
     * Constructor for the MasungPrinterCom class.
     * It calls the initializePrinter method to set up the printer.
     */
    public MasungPrinterCom(Context context) {
        printerService = new PrinterService(context);
    }

    /**
     * Clear cache and reset all paramters
     */
    public void clearCache() {
        printerService.sendBytesToPrinter(PrintCmd.SetClean());
    }

    /**
     * Set left and right margin of the characters
     * Unit: 0.125mm
     *
     * @param left left margin 0-576
     * @param right  right margin 0-576
     */
    public void setMargin(int left,int right) {
        printerService.sendBytesToPrinter(PrintCmd.SetLeftmargin(left));
        printerService.sendBytesToPrinter(PrintCmd.SetRightmargin(right));
    }

    /**
     * Set the alignment of the text
     *
     * @param alignment 0: left, 1: center, 2: right
     */
    public void setAlignment(int alignment) {
        printerService.sendBytesToPrinter(PrintCmd.SetAlignment(alignment));
    }

    /**
     * Set the font size of the text
     *
     * @param width 1-8
     * @param height 1-8
     */
    public void setTextSize(int width,int height) {
        printerService.sendBytesToPrinter(PrintCmd.SetSizetext(width, height));
    }

    /**
     * Set font bold
     *
     * @param bold
     */
    public void setBold(boolean bold) {
        printerService.sendBytesToPrinter(PrintCmd.SetBold(bold ? 1 : 0));
    }

    /**
     * Set font underline
     *
     * @param underline 0: none, 2: single, 3: double
     */
    public void setUnderline(int underline) {
        printerService.sendBytesToPrinter(PrintCmd.SetUnderline(underline));
    }

    /**
     * Set line spacing
     * Unit: 0.125mm
     *
     * @param spacing 0-127
     */
    public void setLineSpacing(int spacing) {
        printerService.sendBytesToPrinter(PrintCmd.SetLinespace(spacing));
    }

    /**
     * Set font italic
     *
     * @param italic
     */
    public void setItalic(boolean italic) {
        printerService.sendBytesToPrinter(PrintCmd.SetItalic(italic ? 1 : 0));
    }

    /**
     * Print string
     *
     * @param text the printed string
     * @param newLine whether to print a new line after the string
     */
    public void printString(String text, boolean newLine) {
        printerService.sendBytesToPrinter(PrintCmd.PrintString(text, newLine ? 0 : 1));
    }

    /**
     * Feeds a number of lines of blank paper
     * @param line number of lines to feed
     */
    public void feedLine(int line) {
        printerService.sendBytesToPrinter(PrintCmd.PrintFeedline(line));
    }

    /**
     * Feeds a number of dots of blank paper
     * @param dots number of dots to feed
     */
    public void feedDot(int dots) {
        printerService.sendBytesToPrinter(PrintCmd.PrintFeedDot(dots));
    }

    /**
     * Cuts the paper
     *
     * @param fullCut whether to perform a full cut or a partial cut
     */
    public void cutPaper(boolean fullCut) {
        printerService.sendBytesToPrinter(PrintCmd.PrintCutpaper(fullCut ? 0 : 1));
    }
}