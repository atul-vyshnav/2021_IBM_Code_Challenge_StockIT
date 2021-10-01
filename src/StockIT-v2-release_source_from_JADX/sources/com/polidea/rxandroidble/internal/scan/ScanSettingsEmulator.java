package com.polidea.rxandroidble.internal.scan;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.util.ObservableUtil;
import com.polidea.rxandroidble.scan.ScanCallbackType;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Func0;
import p030rx.functions.Func1;
import p030rx.observables.GroupedObservable;

public class ScanSettingsEmulator {
    /* access modifiers changed from: private */
    public Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateFirstMatch;
    private Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateFirstMatchAndMatchLost = new Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult>() {
        public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
            return observable.publish(new Func1<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>() {
                public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
                    return Observable.merge(observable.compose(ScanSettingsEmulator.this.emulateFirstMatch), observable.compose(ScanSettingsEmulator.this.emulateMatchLost));
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateMatchLost = new Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult>() {
        public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
            return observable.debounce(10, TimeUnit.SECONDS, ScanSettingsEmulator.this.scheduler).map(ScanSettingsEmulator.this.toMatchLost());
        }
    };
    /* access modifiers changed from: private */
    public final Scheduler scheduler;

    @Inject
    public ScanSettingsEmulator(@Named("computation") final Scheduler scheduler2) {
        this.scheduler = scheduler2;
        this.emulateFirstMatch = new Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult>() {
            /* access modifiers changed from: private */
            public final Func1<RxBleInternalScanResult, Observable<?>> emitAfterTimerFunc = new Func1<RxBleInternalScanResult, Observable<?>>() {
                public Observable<?> call(RxBleInternalScanResult rxBleInternalScanResult) {
                    return C26721.this.timerObservable;
                }
            };
            /* access modifiers changed from: private */
            public final Func1<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>> takeFirstFromEachWindowFunc = new Func1<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>() {
                public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
                    return observable.take(1);
                }
            };
            /* access modifiers changed from: private */
            public final Observable<Long> timerObservable = Observable.timer(10, TimeUnit.SECONDS, scheduler2);
            /* access modifiers changed from: private */
            public Func1<RxBleInternalScanResult, RxBleInternalScanResult> toFirstMatchFunc = ScanSettingsEmulator.this.toFirstMatch();

            public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
                return observable.publish(new Func1<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>() {
                    public Observable<RxBleInternalScanResult> call(final Observable<RxBleInternalScanResult> observable) {
                        return observable.window((Func0<? extends Observable<? extends TClosing>>) new Func0<Observable<?>>() {
                            public Observable<?> call() {
                                return observable.switchMap(C26721.this.emitAfterTimerFunc);
                            }
                        }).flatMap(C26721.this.takeFirstFromEachWindowFunc).map(C26721.this.toFirstMatchFunc);
                    }
                });
            }
        };
    }

    /* access modifiers changed from: package-private */
    public Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateScanMode(int i) {
        if (i == -1) {
            RxBleLog.m1110d("Cannot emulate opportunistic scan mode since it is OS dependent - fallthrough to low power", new Object[0]);
        } else if (i != 0) {
            if (i != 1) {
                return ObservableUtil.identityTransformer();
            }
            return scanModeBalancedTransformer();
        }
        return scanModeLowPowerTransformer();
    }

    private Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> scanModeBalancedTransformer() {
        return repeatedWindowTransformer(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS);
    }

    private Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> scanModeLowPowerTransformer() {
        return repeatedWindowTransformer(500);
    }

    private Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> repeatedWindowTransformer(final int i) {
        final long max = Math.max(TimeUnit.SECONDS.toMillis(5) - ((long) i), 0);
        return new Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult>() {
            public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
                return observable.take((long) i, TimeUnit.MILLISECONDS, ScanSettingsEmulator.this.scheduler).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.delay(max, TimeUnit.MILLISECONDS, ScanSettingsEmulator.this.scheduler);
                    }
                });
            }
        };
    }

    /* access modifiers changed from: package-private */
    public Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateCallbackType(int i) {
        if (i == 2) {
            return splitByAddressAndForEach(this.emulateFirstMatch);
        }
        if (i == 4) {
            return splitByAddressAndForEach(this.emulateMatchLost);
        }
        if (i != 6) {
            return ObservableUtil.identityTransformer();
        }
        return splitByAddressAndForEach(this.emulateFirstMatchAndMatchLost);
    }

    private Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> splitByAddressAndForEach(final Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> transformer) {
        return new Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult>() {
            public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
                return observable.groupBy(new Func1<RxBleInternalScanResult, String>() {
                    public String call(RxBleInternalScanResult rxBleInternalScanResult) {
                        return rxBleInternalScanResult.getBluetoothDevice().getAddress();
                    }
                }).flatMap(new Func1<GroupedObservable<String, RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>() {
                    public Observable<RxBleInternalScanResult> call(GroupedObservable<String, RxBleInternalScanResult> groupedObservable) {
                        return groupedObservable.compose(transformer);
                    }
                });
            }
        };
    }

    /* access modifiers changed from: private */
    public Func1<RxBleInternalScanResult, RxBleInternalScanResult> toFirstMatch() {
        return new Func1<RxBleInternalScanResult, RxBleInternalScanResult>() {
            public RxBleInternalScanResult call(RxBleInternalScanResult rxBleInternalScanResult) {
                return new RxBleInternalScanResult(rxBleInternalScanResult.getBluetoothDevice(), rxBleInternalScanResult.getRssi(), rxBleInternalScanResult.getTimestampNanos(), rxBleInternalScanResult.getScanRecord(), ScanCallbackType.CALLBACK_TYPE_FIRST_MATCH);
            }
        };
    }

    /* access modifiers changed from: private */
    public Func1<RxBleInternalScanResult, RxBleInternalScanResult> toMatchLost() {
        return new Func1<RxBleInternalScanResult, RxBleInternalScanResult>() {
            public RxBleInternalScanResult call(RxBleInternalScanResult rxBleInternalScanResult) {
                return new RxBleInternalScanResult(rxBleInternalScanResult.getBluetoothDevice(), rxBleInternalScanResult.getRssi(), rxBleInternalScanResult.getTimestampNanos(), rxBleInternalScanResult.getScanRecord(), ScanCallbackType.CALLBACK_TYPE_MATCH_LOST);
            }
        };
    }
}
