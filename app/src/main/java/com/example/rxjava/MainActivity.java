package com.example.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String TAG2 = "NainActivity";
    private static final String TAG3 = "LainActivity";
    private static final String TAG4 = "OainActivity";
    private static final String TAG5 = "PainActivity";
    private Subscription ms;
    private String stext;
    private Button read;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        read = findViewById(R.id.read);
        text = findViewById(R.id.text);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRead();
                try {
                    Thread.sleep(10000000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //创建被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            private int i;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "" + integer);
                i++;
                if(i == 2){
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });




        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Log.d(TAG,Thread.currentThread().getName());
            }
        });
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"" + integer);
                Log.d(TAG,Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable2.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer2);




        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new io.reactivex.functions.Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result" + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new io.reactivex.functions.Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    list.add("I am a value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new io.reactivex.functions.Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    list.add("I am a value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        Observable<Integer> observable5 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Log.d(TAG2,"emitter1");
                emitter.onNext(2);
                Log.d(TAG2,"emitter2");
                emitter.onNext(3);
                Log.d(TAG2,"emitter3");
                emitter.onNext(4);
                Log.d(TAG2,"emitter4");
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable6 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                Log.d(TAG2,"emitterA");
                emitter.onNext("B");
                Log.d(TAG2,"emitterB");
                emitter.onNext("C");
                Log.d(TAG2,"emitterC");
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable5, observable6, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG2,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(int i = 0;;i++)
                    emitter.onNext(i);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer % 1000 == 0;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG3,"" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(int i =0 ; ; i++){
                    emitter.onNext(i);
                }
            }
        }).sample(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG4,"" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Log.d(TAG5,"emitter1");
                emitter.onNext(2);
                Log.d(TAG5,"emitter2");
                emitter.onNext(3);
                Log.d(TAG5,"emitter3");
                emitter.onNext(4);
                Log.d(TAG5,"emitter4");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Subscriber<Integer>() {

            private Subscription subscription;
            private int i;
            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                s.request(128);
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                Log.d(TAG5,integer + "");
                if(i == 2){
                    subscription.cancel();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void startRead(){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                try{
                    FileReader reader = new FileReader("text.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String string;
                    while ((string = bufferedReader.readLine()) != null && !emitter.isCancelled()){
                        while (emitter.requested() == 0){
                            if(emitter.isCancelled()){
                                break;
                            }
                        }
                        emitter.onNext(string);
                    }
                    bufferedReader.close();
                    reader.close();
                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        ms = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.print(s);
                        stext = stext + s;
                        try {
                            Thread.sleep(2000);
                            ms.request(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
