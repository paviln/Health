package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.DecimalFormat;

public class Controller
{
    @FXML private TextField workoutHours;
    @FXML private TextField workoutWeek;
    @FXML private TextArea history;
    @FXML private TextField avgField;
    @FXML private TextField inputWeight;
    @FXML private TextField inputHeight;
    @FXML private TextField tip;
    @FXML private TextField bmi;

    public void HandleBMI()
    {
        double value = 0;
        double weight = 0;
        double height = 0;

        bmi.setText("");

        try
        {
            weight = Double.parseDouble(inputWeight.getText());
        }
        catch(NumberFormatException e)
        {
            inputWeight.setText("Not valid");
        }

        try
        {
            height = Double.parseDouble(inputHeight.getText())/100;
        }
        catch(NumberFormatException e)
        {
            inputHeight.setText("Not valid");
        }

        if (weight > 0 && height > 0)
        {
            value = weight/Math.pow(height, 2);
            DecimalFormat f = new DecimalFormat("##.0");
            bmi.setText(String.valueOf(f.format(value)));
        }
        String bmiTip = "";

        if (value > 0 && value < 18.5)
        {
            bmiTip = "Go to McDonalds!!";
        }
        else if (value >= 18.5 && value <= 25)
        {
            bmiTip = "Keep going - healthy!";
        }
        else if (value > 25 && value < 30)
        {
            bmiTip = "Eas down on carbs";
        }
        else if (value > 30)
        {
            bmiTip = "Stop Eating, NOW!!";
        }
        tip.setText(bmiTip);
    }

    // Clear workoutWeek TextField
    public void clearWeek()
    {
        workoutWeek.clear();
    }

    // Clear workoutHours TextField
    public void clearHours()
    {
        workoutHours.clear();
    }

    // Clear weight TextField
    public void clearWeight()
    {
        inputWeight.clear();
    }

    // Clear height TextField
    public void clearHeight()
    {
        inputHeight.clear();
    }

    // Add or update workout to database
    public void workout()
    {
        int week = 0;
        int hours = 0;
        try
        {
            week = Integer.parseInt(workoutWeek.getText());
        }
        catch(NumberFormatException e)
        {
            workoutWeek.setText("Not valid");
        }

        try
        {
            hours = Integer.parseInt(workoutHours.getText());
        }
        catch(NumberFormatException e)
        {
            workoutHours.setText("Not valid");
        }

        if (week > 0 && week <= 52 && hours > 0 && hours <= 168)
        {
            boolean exist = false;
            String value = "";
            DB.selectSQL("SELECT totalHours FROM workout WHERE weekNumber=" + week);
            do
            {
                String data = DB.getData();
                if (data.equals(DB.NOMOREDATA))
                {
                    break;
                }
                else
                {
                    value = data;
                    exist = true;
                }
            } while (true);
            if (exist && !value.equals(workoutHours.getText()))
            {
                DB.updateSQL("UPDATE workout SET totalHours='" + hours + "' where weekNumber='" + week + "'");
            }
            else if (!value.equals(workoutHours.getText()))
            {
                DB.insertSQL("INSERT INTO workout values(" + week + "," + hours + ")");
            }
        }
        else if (week <= 0 || week > 52)
        {
            workoutWeek.setText("Not valid");
        }
        else if (hours < 0 || hours > 168)
        {
            workoutHours.setText("Not valid");
        }
        DisplayHist();
        AvgData();
    }

    // Show average workout time last 3 weeks
    public void AvgData()
    {
        String[] avg = new String[3];

        DB.selectSQL("SELECT TOP 3 totalHours FROM  workout ORDER  BY  1 DESC");

        int i = 0;
        do
        {
            String data = DB.getDisplayData();
            if (data.equals(DB.NOMOREDATA))
            {
                break;
            } else
            {
                avg[i] = data;
                i++;
            }
        } while (true);

        DecimalFormat f = new DecimalFormat("##.0");
        double value = (Double.parseDouble(avg[0]) + Double.parseDouble(avg[1]) + Double.parseDouble(avg[2]))/3;
        avgField.setText(String.valueOf(f.format(value)));
    }

    // Show the history of all workout weeks
    public void DisplayHist()
    {
        // Variable Declarations
        String TotalHours = "";
        String CheckID = "";
        String WeekNo = "";

        history.clear();

        DB.selectSQL("select MAX(weekNumber) from workout");

        do
        {
            String data = DB.getDisplayData();
            if (data.equals(DB.NOMOREDATA))
            {
                break;
            }
            else
            {
                CheckID = data;
            }
        } while(true);
        int id = Integer.parseInt(CheckID.trim());

        for(int i = 0; i <= id; i++ )
        {
            DB.selectSQL("SELECT totalHours FROM workout WHERE weekNumber =" + i);

            do
            {
                String data = DB.getDisplayData();
                if (data.equals(DB.NOMOREDATA))
                {
                    break;
                }
                else
                {
                    TotalHours = data;
                }
            } while(true);

            DB.selectSQL("SELECT weekNumber FROM workout WHERE weekNumber =" + i);

            do
            {
                String data = DB.getDisplayData();
                if (data.equals(DB.NOMOREDATA))
                {
                    break;
                }
                else
                {
                    WeekNo = data;
                    history.appendText("Week No: " + WeekNo + "TotalHours: " + TotalHours);
                }
            } while(true);
        }
    }

    // Run on program start
    public void initialize()
    {
        DisplayHist();
        AvgData();

        // Listen for workoutWeek TextField text changes
        workoutWeek.textProperty().addListener((observable, oldValue, newValue) ->
        {
            int value = 0;

            try
            {
                value = Integer.parseInt(newValue);
            }
            catch(NumberFormatException e)
            {
                System.out.println("input is not an int value");
            }

            if (value > 0 && value <= 52)
            {
                workoutHours.setText("");
                DB.selectSQL("SELECT totalHours FROM workout WHERE weekNumber=" + value);
                do
                {
                    String data = DB.getData();
                    if (data.equals(DB.NOMOREDATA))
                    {
                        break;
                    } else
                    {
                        workoutHours.setText(data);
                    }
                } while (true);
            }
        });
    }
}