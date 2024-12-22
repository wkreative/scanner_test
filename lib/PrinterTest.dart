import 'package:flutter/material.dart';
import 'package:masung_flutter/masung_flutter.dart';
import 'package:masung_flutter/model/alignment_enum.dart';
import 'package:masung_flutter/model/under_line_enum.dart';

class PrintPage extends StatefulWidget {
  @override
  _PrintPageState createState() => _PrintPageState();
}

class _PrintPageState extends State<PrintPage> {
  final MasungFlutter masungFlutter = MasungFlutter();
  final TextEditingController _controller = TextEditingController();

  // Form fields for text settings
  int _fontSize = 2;
  int _lineSpacing = 0;
  int _margin = 0;
  int _leftMargin = 0;
  int _rightMargin = 0;
  bool _isBold = false;
  bool _isItalic = false;
  UnderLineEnum _underline = UnderLineEnum.none;
  AlignmentEnum _alignment = AlignmentEnum.left;

  // Method to apply all settings and print the text
  Future<void> _applySettingsAndPrint() async {
    // Set all the configurations
    await masungFlutter.setFontSize(fontSize: _fontSize);
    await masungFlutter.setLineSpace(_lineSpacing);
    await masungFlutter.setMargin(margin: _margin, left: _leftMargin, right: _rightMargin);
    await masungFlutter.setFontBold(_isBold);
    await masungFlutter.setItalic(_isItalic);
    await masungFlutter.setFontUnderline(_underline);
    await masungFlutter.setAlignment(_alignment);

    // Now print the text
    String textToPrint = _controller.text;
    if (textToPrint.isEmpty) {
      _showDialog('Please enter text to print.');
    } else {
      bool? isPrinted = await masungFlutter.printString(textToPrint, newLine: true);

      if (isPrinted ?? false) {
        _showDialog('Text printed successfully!');
      } else {
        _showDialog('Failed to print the text.');
      }
    }
  }

  // Helper method to show a simple dialog
  void _showDialog(String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Print Status'),
          content: Text(message),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('OK'),
            ),
          ],
        );
      },
    );
  }

  // Widget to create a setting row (for margin, font size, etc.)
  Widget _settingRow(String label, Widget settingWidget) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: TextStyle(fontSize: 16)),
          settingWidget,
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Print Settings'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              // Text input field
              TextField(
                controller: _controller,
                decoration: InputDecoration(
                  labelText: 'Enter text to print',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 20),

              // Font size setting
              _settingRow(
                'Font Size',
                DropdownButton<int>(
                  value: _fontSize,
                  items: List.generate(8, (index) => index + 1).map((value) {
                    return DropdownMenuItem<int>(
                      value: value,
                      child: Text('$value'),
                    );
                  }).toList(),
                  onChanged: (value) {
                    setState(() {
                      _fontSize = value!;
                    });
                  },
                ),
              ),

              // Line spacing setting
              _settingRow(
                'Line Spacing',
                Slider(
                  value: _lineSpacing.toDouble(),
                  min: 0,
                  max: 127,
                  divisions: 127,
                  label: _lineSpacing.toString(),
                  onChanged: (value) {
                    setState(() {
                      _lineSpacing = value.toInt();
                    });
                  },
                ),
              ),

              // Margin setting
              _settingRow(
                'Margin (0.125mm units)',
                Slider(
                  value: _margin.toDouble(),
                  min: 0,
                  max: 576,
                  divisions: 576,
                  label: _margin.toString(),
                  onChanged: (value) {
                    setState(() {
                      _margin = value.toInt();
                    });
                  },
                ),
              ),

              // Left margin setting
              _settingRow(
                'Left Margin',
                Slider(
                  value: _leftMargin.toDouble(),
                  min: 0,
                  max: 576,
                  divisions: 576,
                  label: _leftMargin.toString(),
                  onChanged: (value) {
                    setState(() {
                      _leftMargin = value.toInt();
                    });
                  },
                ),
              ),

              // Right margin setting
              _settingRow(
                'Right Margin',
                Slider(
                  value: _rightMargin.toDouble(),
                  min: 0,
                  max: 576,
                  divisions: 576,
                  label: _rightMargin.toString(),
                  onChanged: (value) {
                    setState(() {
                      _rightMargin = value.toInt();
                    });
                  },
                ),
              ),

              // Bold setting
              _settingRow(
                'Bold',
                Switch(
                  value: _isBold,
                  onChanged: (value) {
                    setState(() {
                      _isBold = value;
                    });
                  },
                ),
              ),

              // Italic setting
              _settingRow(
                'Italic',
                Switch(
                  value: _isItalic,
                  onChanged: (value) {
                    setState(() {
                      _isItalic = value;
                    });
                  },
                ),
              ),

              // Underline setting
              _settingRow(
                'Underline',
                DropdownButton<UnderLineEnum>(
                  value: _underline,
                  items: UnderLineEnum.values.map((value) {
                    return DropdownMenuItem<UnderLineEnum>(
                      value: value,
                      child: Text(value.toString().split('.').last),
                    );
                  }).toList(),
                  onChanged: (value) {
                    setState(() {
                      _underline = value!;
                    });
                  },
                ),
              ),

              // Alignment setting
              _settingRow(
                'Text Alignment',
                DropdownButton<AlignmentEnum>(
                  value: _alignment,
                  items: AlignmentEnum.values.map((value) {
                    return DropdownMenuItem<AlignmentEnum>(
                      value: value,
                      child: Text(value.toString().split('.').last),
                    );
                  }).toList(),
                  onChanged: (value) {
                    setState(() {
                      _alignment = value!;
                    });
                  },
                ),
              ),

              SizedBox(height: 20),

              // Print Button
              ElevatedButton(
                onPressed: _applySettingsAndPrint,
                child: Text('Print Text'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
