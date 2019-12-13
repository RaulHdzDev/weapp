package com.example.inicio_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.TtsSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    CurvedBottomNavigationView bottomNavigationView;
    VectorMasterView fab, fab1, fab2;
    RelativeLayout lin_id;
    PathModel outline;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Fragment fragmentclick = null;
        bottomNavigationView = (CurvedBottomNavigationView)findViewById(R.id.bottom_nav);
        fab = (VectorMasterView) findViewById(R.id.fab1);
        fab1 = (VectorMasterView) findViewById(R.id.fab);
        fab2 = (VectorMasterView) findViewById(R.id.fab2);

        lin_id = (RelativeLayout) findViewById(R.id.lin_id);
        bottomNavigationView.inflateMenu(R.menu.main_menu);
        fragmentclick = new Inicio_fragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentclick).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragmentclick = null;
        switch (menuItem.getItemId()){

            case R.id.action_home:
                Toast.makeText(this,"¡BIENVENIDO DE NUEVO!",Toast.LENGTH_SHORT).show();
                draw(2 );
                lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.GONE);
                fragmentclick = new Inicio_fragment();

                drawAnimation(fab1);
                break;

            case R.id.action_contenedores:
                Toast.makeText(this,"¡TUS CONTENEDORES!",Toast.LENGTH_SHORT).show();
                draw();
                lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                fragmentclick = new Contenedores_fragment();
                drawAnimation(fab);
                break;

            case R.id.action_agenda:
                Toast.makeText(this,"¡WE AGENDA!",Toast.LENGTH_SHORT).show();
                draw(6);
                lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.VISIBLE);
                fragmentclick = new Agenda_fragment();
                drawAnimation(fab2);
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.transition_der_izq, R.anim.exit_der_izq,
                        R.anim.transition_izq_der,R.anim.exit_izq_der)

                .replace(R.id.fragment_container,fragmentclick).commit();
        return  true;
    }

    private void draw() {
        bottomNavigationView.mFirstCurveStartPoint.set( (bottomNavigationView.mNavigationBarWidth * 10/12) - (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)
        - (bottomNavigationView.CURVE_CIIRCLE_RADIUS/3),0);

        bottomNavigationView.mFirstCurveEndPoint.set( (bottomNavigationView.mNavigationBarWidth * 10/12), bottomNavigationView.CURVE_CIIRCLE_RADIUS +
                (bottomNavigationView.CURVE_CIIRCLE_RADIUS/4));

        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;

        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth * 10/12) + (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)+
                (bottomNavigationView.CURVE_CIIRCLE_RADIUS/3),0);

        bottomNavigationView.mFirstCurveControlPoint1.set( bottomNavigationView.mFirstCurveStartPoint.x + bottomNavigationView.CURVE_CIIRCLE_RADIUS
                + (bottomNavigationView.CURVE_CIIRCLE_RADIUS/4), bottomNavigationView.mFirstCurveStartPoint.y);

        bottomNavigationView.mFirstCurveControlPoint2.set( bottomNavigationView.mFirstCurveEndPoint.x - (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)
                + bottomNavigationView.CURVE_CIIRCLE_RADIUS, bottomNavigationView.mFirstCurveEndPoint.y);

        bottomNavigationView.mSecondCurveControlPoint1.set( bottomNavigationView.mSecondCurveStartPoint.x + (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)
                - bottomNavigationView.CURVE_CIIRCLE_RADIUS, bottomNavigationView.mSecondCurveStartPoint.y);

        bottomNavigationView.mSecondCurveControlPoint2.set( bottomNavigationView.mSecondCurveEndPoint.x - (bottomNavigationView.CURVE_CIIRCLE_RADIUS +
                (bottomNavigationView.CURVE_CIIRCLE_RADIUS/4)), bottomNavigationView.mSecondCurveEndPoint.y );

    }

    private void drawAnimation(final VectorMasterView fab) {

        outline=fab.getPathModelByName("outline");
        outline.setStrokeColor(Color.WHITE);

        outline.setTrimPathEnd(0.0f);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                fab.update();
            }
        });

        valueAnimator.start();
    }

    private void draw(int i) {
        bottomNavigationView.mFirstCurveStartPoint.set( (bottomNavigationView.mNavigationBarWidth/i) - (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)
                - (bottomNavigationView.CURVE_CIIRCLE_RADIUS/3),0 );

        bottomNavigationView.mFirstCurveEndPoint.set( bottomNavigationView.mNavigationBarWidth/i, bottomNavigationView.CURVE_CIIRCLE_RADIUS +
                (bottomNavigationView.CURVE_CIIRCLE_RADIUS/4) );

        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;

        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth/i) + (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)+
                (bottomNavigationView.CURVE_CIIRCLE_RADIUS/3),0);

        bottomNavigationView.mFirstCurveControlPoint1.set( bottomNavigationView.mFirstCurveStartPoint.x + bottomNavigationView.CURVE_CIIRCLE_RADIUS
                + (bottomNavigationView.CURVE_CIIRCLE_RADIUS/4), bottomNavigationView.mFirstCurveStartPoint.y);

        bottomNavigationView.mFirstCurveControlPoint2.set( bottomNavigationView.mFirstCurveEndPoint.x - (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)
                + bottomNavigationView.CURVE_CIIRCLE_RADIUS, bottomNavigationView.mFirstCurveEndPoint.y);

        bottomNavigationView.mSecondCurveControlPoint1.set( bottomNavigationView.mSecondCurveStartPoint.x + (bottomNavigationView.CURVE_CIIRCLE_RADIUS*2)
                - bottomNavigationView.CURVE_CIIRCLE_RADIUS, bottomNavigationView.mSecondCurveStartPoint.y);

        bottomNavigationView.mSecondCurveControlPoint2.set( bottomNavigationView.mSecondCurveEndPoint.x - (bottomNavigationView.CURVE_CIIRCLE_RADIUS +
                (bottomNavigationView.CURVE_CIIRCLE_RADIUS/4)), bottomNavigationView.mSecondCurveEndPoint.y );
    }
}
