package com.pramu.idare;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.pramu.idare.Activity.IntroActivity;
import com.pramu.idare.Adapter.DareAdapter;
import com.pramu.idare.Adapter.TruthAdapter;
import com.pramu.idare.Utils.GameModel;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Button initTruthBtn, initDareBtn, addQuestionBtn, addTruthBtn, addDareBtn,startgamebtn,spinmulaibtn,spinselesaibtn,
            viewDaresHomePageBtn, viewTruthsHomePageBtn, viewTruthsBtn, viewDaresBtn,open,spin,opend,spind,completebtn;
    private ImageButton settingsBtn, backBtn, addQuestionBackBtn, viewDaresBackBtn, viewTruthsBackBtn,caramain;
    private TextView chosenMode, randomPhrase, infospin;
    private EditText userQuestion;
    private ListView listViewDares, listViewTruths;
    private GameModel gameModel;
    private ImageView bottlespin;
    Random rand = new Random();
    int lastAngle = -1;
    LottieAnimationView imgIconLike,done;
    InterstitialAd interstitialAd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadBannerAds();
        createInterstitial();
        gameModel = new GameModel(this);
        initWidgets();

    }

    @Override
    public void onBackPressed() {

    }
    private void showButtonsTruth() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Button) findViewById(R.id.open)).setVisibility(View.VISIBLE);
            }
        }, 3000);
    }
    private void showButtonsDare() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Button) findViewById(R.id.opendare)).setVisibility(View.VISIBLE);
            }
        }, 3000);
    }
    private void showButtonsSelesaiSpin() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Button) findViewById(R.id.spinselesai)).setVisibility(View.VISIBLE);
                ((Button) findViewById(R.id.spinmulai)).setVisibility(View.VISIBLE);

            }
        }, 3000);
    }

    // Initialize the home page buttons.
    private void initWidgets() {
        startgamebtn = findViewById(R.id.startgame);
        startgamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_bottle_spin);
                showInterstitial();
                infospin = findViewById(R.id.tvspinbottle);
                bottlespin = findViewById(R.id.bottlespin);
                spinmulaibtn = findViewById(R.id.spinmulai);
                spinselesaibtn = findViewById(R.id.spinselesai);
                spinmulaibtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinBottle();
                        infospin.setVisibility(View.INVISIBLE);
                        spinmulaibtn.setVisibility(View.INVISIBLE);
                        showButtonsSelesaiSpin();
                    }
                });
                spinselesaibtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opengametruth(0);
                    }
                });

            }
        });


        addQuestionBtn = findViewById(R.id.goToAddQuestionBtn);
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddQuestion();
            }
        });
        viewDaresHomePageBtn = findViewById(R.id.viewDareQuestionHomeBtn);
        viewDaresHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewDareQuestions(0);
            }
        });
        viewTruthsHomePageBtn = findViewById(R.id.viewTruthQuestionHomeBtn);
        viewTruthsHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewTruthQuestions(0);
            }
        });
        settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewSettings();
            }
        });
        caramain = findViewById(R.id.carabermain);
        caramain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    // Initialize the "game page" widgets.
    private void initGamePageButtons() {

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
    }


    // Open the addQuestion screen.
    private void openAddQuestion() {
        // Initialize the widgets
        showInterstitial();
        setContentView(R.layout.activity_add_question);
        loadBannerAds3();
        userQuestion = findViewById(R.id.userQuestionEntry);
        addQuestionBackBtn = findViewById(R.id.addQuestionBackBtn);
        addTruthBtn = findViewById(R.id.addToTruthBtn);
        addDareBtn = findViewById(R.id.addToDareBtn);
        addTruthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userQuestion.getText().toString().length() == 0) {
                    return;
                }
                // Add the users question to the database
                gameModel.addToTruthsDB(userQuestion.getText().toString());
                userQuestion.getText().clear();
                Toast.makeText(MainActivity.this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });
        addDareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userQuestion.getText().toString().length() == 0) {
                    return;
                }
                // Add the users question to the database
                gameModel.addToDaresDB(userQuestion.getText().toString());
                userQuestion.getText().clear();
                Toast.makeText(MainActivity.this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        addQuestionBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
    }


    public void openViewSettings() {
        showInterstitial();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Apakah anda ingin keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // Return to the home screen and re-fill both ArrayLists.
    private void backToHome() {
        setContentView(R.layout.activity_main);
        initWidgets();
        gameModel.reFillQuestions(this);
    }


    // Mode: 0 = home page, 1 = game page
    // Retrieve a truth question and set the new content view
    public void openTruth(int mode) {
        if (mode == 0) {
            setContentView(R.layout.activity_game);
        }
        done = findViewById(R.id.img6);
        done.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                done.setVisibility(View.GONE);
                backBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        completebtn = findViewById(R.id.completebtn);
        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setVisibility(View.VISIBLE);
                done.playAnimation();
                Toast.makeText(MainActivity.this, "Kejujuran selesai", Toast.LENGTH_SHORT).show();
            }
        });

        chosenMode = findViewById(R.id.t_or_f);
        randomPhrase = findViewById(R.id.rndmPhrase);
        chosenMode.setText(gameModel.switchTruth());
        randomPhrase.setText(gameModel.getTruth());
        initGamePageButtons();
    }


    // Mode: 0 = home page, 1 = game page
    // Retrieve a dare question and set the new content view
    public void openDare(int mode) {
        if (mode == 0) {
            setContentView(R.layout.activity_game);
        }
        done = findViewById(R.id.img6);
        done.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                done.setVisibility(View.GONE);
                backBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        completebtn = findViewById(R.id.completebtn);
        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setVisibility(View.VISIBLE);
                done.playAnimation();
                Toast.makeText(MainActivity.this, "Tantangan selesai", Toast.LENGTH_SHORT).show();
            }
        });
        chosenMode = findViewById(R.id.t_or_f);
        randomPhrase = findViewById(R.id.rndmPhrase);
        chosenMode.setText(gameModel.switchDare());
        randomPhrase.setText(gameModel.getDare());
        initGamePageButtons();
    }


    // Will probably want a different class for the layout maker
    // Mode = 0 means the content view must be changed, 1 means the content view is already the current view.
    private void openViewDareQuestions(int mode) {
       showInterstitial();
        gameModel.reFillQuestions(this); // ensures previously added questions show up
        if (mode == 0){
            setContentView(R.layout.activity_dare_data);
        }
        viewTruthsBtn = findViewById(R.id.viewTruthQuestions);
        viewTruthsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewTruthQuestions(0);
            }
        });
        viewDaresBackBtn = findViewById(R.id.viewDareQuestionBackBtn);
        viewDaresBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
        listViewDares = findViewById(R.id.dareQuestionsListView);
        DareAdapter dareAdapter = new DareAdapter(gameModel,this);
        listViewDares.setAdapter(dareAdapter);
    }

    // Mode = 0 means the content view must be changed, 1 means the content view is already the current view.
    private void openViewTruthQuestions(int mode) {
        showInterstitial();
        gameModel.reFillQuestions(this); // ensures previously added questions show up
        if (mode == 0){
            setContentView(R.layout.activity_truth_data);
        }
        viewDaresBtn = findViewById(R.id.viewDareQuestions);
        viewDaresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewDareQuestions(0);
            }
        });
        viewTruthsBackBtn = findViewById(R.id.viewTruthQuestionBackBtn);
        viewTruthsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
        listViewTruths = findViewById(R.id.truthQuestionsListView);
        TruthAdapter truthAdapter = new TruthAdapter(gameModel,this);
        listViewTruths.setAdapter(truthAdapter);
    }

    private void opengametruth(int mode){
        setContentView(R.layout.activity_main2);
        loadBannerAds2();
        initTruthBtn = findViewById(R.id.initTruthButton);
        initTruthBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                setContentView(R.layout.activity_spin_truth);
                loadBannerAds4();
                imgIconLike = findViewById(R.id.bottle);
                open = findViewById(R.id.open);
                spin = findViewById(R.id.spin);
                spin.setOnClickListener(new View.OnClickListener() {
                    boolean isAnimated=false;
                    @Override
                    public void onClick(View view) {
                        if (!isAnimated){
                            isAnimated=true;
                            imgIconLike.playAnimation();
                            spin.setVisibility(View.INVISIBLE);
                        }
                        showButtonsTruth();

                    }
                });

                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openTruth(0);
                    }
                });
            }
        });
        initDareBtn = findViewById(R.id.initDareButton);
        initDareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setContentView(R.layout.activity_spin_dare);
                loadBannerAds5();
                imgIconLike = findViewById(R.id.bottle);
                opend = findViewById(R.id.opendare);
                spind= findViewById(R.id.spindare);
                spind.setOnClickListener(new View.OnClickListener() {
                    boolean isAnimated=false;
                    @Override
                    public void onClick(View view) {
                        if (!isAnimated){
                            isAnimated=true;
                            imgIconLike.playAnimation();
                            spind.setVisibility(View.INVISIBLE);
                        }
                        showButtonsDare();

                    }
                });

                opend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDare(0);
                    }
                });
            }
        });
    }

    //Putar Botol
    private void spinBottle() {

        int angle = rand.nextInt(3600-360) + 360;
        float pivotX = bottlespin.getWidth()/2;
        float pivotY = bottlespin.getHeight()/2;

        final Animation animRotate = new RotateAnimation(lastAngle == -1 ? 0 : lastAngle, angle, pivotX, pivotY);
        lastAngle = angle;
        animRotate.setDuration(2500);
        animRotate.setFillAfter(true);

        bottlespin.startAnimation(animRotate);
    }

    //Tampilkan Iklannn
    public void loadBannerAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void loadBannerAds2() {
        AdView mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void loadBannerAds3() {
        AdView mAdView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void loadBannerAds4() {
        AdView mAdView = (AdView) findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void loadBannerAds5() {
        AdView mAdView = (AdView) findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void createInterstitial() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-7496679438317923/9403881685"); // Ganti sesuai dengan kode interstitial ads anda
        loadInterstitial();
    }

    public void loadInterstitial() {
        AdRequest interstitialRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interstitialRequest);
    }

    public void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // not call show interstitial ad from here
                }

                @Override
                public void onAdClosed() {
                    loadInterstitial();
                }
            });
        } else {
            loadInterstitial();
        }
    }


}
