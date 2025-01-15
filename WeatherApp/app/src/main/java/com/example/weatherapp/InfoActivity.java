package com.example.weatherapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity); // Connects to the new layout

        // Find the TextView by its ID
        TextView infoText = findViewById(R.id.infoText);

        // Set the text programmatically
        infoText.setText("The Product Manager Accelerator Program is designed to support PM professionals through every stage of their careers. From students looking for entry-level jobs to Directors looking to take on a leadership role, our program has helped over hundreds of students fulfill their career aspirations.\n" +
                "\n" +
                "Our Product Manager Accelerator community are ambitious and committed. Through our program they have learnt, honed and developed new PM and leadership skills, giving them a strong foundation for their future endeavors.\n" +
                "\n" +
                "Here are the examples of services we offer. Check out our website (link under my profile) to learn more about our services.\n" +
                "\n" +
                "\uD83D\uDE80 PMA Pro\n" +
                "End-to-end product manager job hunting program that helps you master FAANG-level Product Management skills, conduct unlimited mock interviews, and gain job referrals through our largest alumni network. 25% of our offers came from tier 1 companies and get paid as high as $800K/year. \n" +
                "\n" +
                "\uD83D\uDE80 AI PM Bootcamp\n" +
                "Gain hands-on AI Product Management skills by building a real-life AI product with a team of AI Engineers, data scientists, and designers. We will also help you launch your product with real user engagement using our 100,000+ PM community and social media channels. \n" +
                "\n" +
                "\uD83D\uDE80 PMA Power Skills\n" +
                "Designed for existing product managers to sharpen their product management skills, leadership skills, and executive presentation skills\n" +
                "\n" +
                "\uD83D\uDE80 PMA Leader\n" +
                "We help you accelerate your product management career, get promoted to Director and product executive levels, and win in the board room. \n" +
                "\n" +
                "\uD83D\uDE80 1:1 Resume Review\n" +
                "We help you rewrite your killer product manager resume to stand out from the crowd, with an interview guarantee.\u2028\u2028Get started by using our FREE killer PM resume template used by over 14,000 product managers. https://www.drnancyli.com/pmresume\n" +
                "\n" +
                "\uD83D\uDE80 We also published over 500+ free training and courses. Please go to my YouTube channel https://www.youtube.com/c/drnancyli and Instagram @drnancyli to start learning for free today.\n" +
                "\n" +
                "Website\n" +
                "https://www.pmaccelerator.io/\n" +
                "Phone\n" +
                "+19548891063Phone number is +19548891063\n" +
                "Industry\n" +
                "E-Learning Providers\n" +
                "Company size\n" +
                "2-10 employees\n" +
                "87 associated members LinkedIn members who’ve listed Product Manager Accelerator as their current workplace on their profile.\n" +
                "Headquarters\n" +
                "Boston, MA\n" +
                "Founded\n" +
                "2020\n" +
                "Specialties\n" +
                "Product Management, Product Manager, Product Management Training, Product Management Certification, Product Lead, Product Executive, Associate Product Manager, product management coaching, product manager resume, Product Management Interview, VP of Product, Director of Product, Chief Product Officer, and AI Product Management");
    }
}
