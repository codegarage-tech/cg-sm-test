package tech.codegarage.apkbackup.fragment;

import android.content.Intent;
import android.view.View;

import tech.codegarage.apkbackup.R;
import tech.codegarage.apkbackup.base.BaseFragment;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class HomeFragment extends BaseFragment implements ScreenShotable {

//    private View containerView;
    //    protected ImageView mImageView;
//    protected int res;
//    private Bitmap bitmap;

    @Override
    public int initFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initFragmentViews(View parentView) {
//        containerView = parentView.findViewById(R.id.container);
    }

    @Override
    public void initFragmentViewsData() {

    }

    @Override
    public void initFragmentActions() {

    }

    @Override
    public void initFragmentBackPress() {

    }

    @Override
    public void initFragmentOnResult(int requestCode, int resultCode, Intent data) {

    }

//
//    public static BackupFragment newInstance(int resId) {
//        BackupFragment contentFragment = new BackupFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(Integer.class.getName(), resId);
//        contentFragment.setArguments(bundle);
//        return contentFragment;
//    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        this.containerView = view.findViewById(R.id.container);
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        res = getArguments().getInt(Integer.class.getName());
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
////        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
////        mImageView.setClickable(true);
////        mImageView.setFocusable(true);
////        mImageView.setImageResource(res);
//        return rootView;
//    }

//    @Override
//    public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                HomeFragment.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();
//
//    }
//
//    @Override
//    public Bitmap getBitmap() {
//        return bitmap;
//    }
}