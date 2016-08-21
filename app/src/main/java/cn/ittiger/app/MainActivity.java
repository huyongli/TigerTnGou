package cn.ittiger.app;

import cn.ittiger.app.base.app.TigerApplication;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.base.util.UIUtil;
import cn.ittiger.app.tiangou.fragment.FragmentFactory;
import cn.ittiger.app.tiangou.type.TGModelEnum;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(id=R.id.toolbar)
    private Toolbar mToolbar;
    @InjectView(id=R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    @InjectView(id=R.id.nav_view)
    private NavigationView mNavigationView;

    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectHelper.inject(this);

        initView();

        init();
    }

    private void init() {

        mFragmentSparseArray = new SparseArray<>();
        Fragment fragment = FragmentFactory.createClassifyFragment(TGModelEnum.MODEL_PHOTO);
        switchFragment(fragment);
        mFragmentSparseArray.put(R.id.nav_photo, fragment);
        mNavigationView.setCheckedItem(R.id.nav_photo);
    }

    private void initView() {

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - exitTime > 2000) {
                UIUtil.showToast(this, R.string.two_click_exit_app);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ((TigerApplication)getApplication()).onDestory();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.isChecked()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        int id = item.getItemId();
        Fragment fragment = mFragmentSparseArray.get(id);
        if(fragment == null) {
            switch (id) {
                case R.id.nav_photo:
                    if(fragment == null) {
                        fragment = FragmentFactory.createClassifyFragment(TGModelEnum.MODEL_PHOTO);
                    }
                    break;
                case R.id.nav_cook:
                    fragment = FragmentFactory.createClassifyFragment(TGModelEnum.MODEL_COOK);
                    break;
                case R.id.nav_health_lore:
                    fragment = FragmentFactory.createClassifyFragment(TGModelEnum.MODEL_HEALTH_LORE);
                    break;
            }
        }
        if(fragment != null) {
            mFragmentSparseArray.put(id, fragment);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if(fragment != null) {
            switchFragment(fragment);
        }
        return true;
    }

    /**
     * 切换界面
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_main, fragment);

        fragmentTransaction.commit();
    }
}
