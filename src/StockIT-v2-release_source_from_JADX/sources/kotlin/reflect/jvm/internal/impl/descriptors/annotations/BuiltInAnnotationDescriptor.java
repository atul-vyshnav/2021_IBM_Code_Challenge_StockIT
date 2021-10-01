package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* compiled from: BuiltInAnnotationDescriptor.kt */
public final class BuiltInAnnotationDescriptor implements AnnotationDescriptor {
    private final Map<Name, ConstantValue<?>> allValueArguments;
    /* access modifiers changed from: private */
    public final KotlinBuiltIns builtIns;
    private final FqName fqName;
    private final Lazy type$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new BuiltInAnnotationDescriptor$type$2(this));

    public BuiltInAnnotationDescriptor(KotlinBuiltIns kotlinBuiltIns, FqName fqName2, Map<Name, ? extends ConstantValue<?>> map) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "builtIns");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(map, "allValueArguments");
        this.builtIns = kotlinBuiltIns;
        this.fqName = fqName2;
        this.allValueArguments = map;
    }

    public FqName getFqName() {
        return this.fqName;
    }

    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return this.allValueArguments;
    }

    public KotlinType getType() {
        Object value = this.type$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "pyright 2010-2017 JetBrains s.r.o.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n * http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n\npackage org.jetbrains.kotlin.descriptors.annotations\n\nimport org.jetbrains.kotlin.builtins.KotlinBuiltIns\nimport org.jetbrains.kotlin.descriptors.SourceElement\nimport org.jetbrains.kotlin.name.FqName\nimport org.jetbrains.kotlin.name.Name\nimport org.jetbrains.kotlin.resolve.constants.ConstantValue\nimport org.jetbrains.kotlin.types.KotlinType\nimport kotlin.LazyThreadSafetyMode.PUBLICATION\n\nclass BuiltInAnnotationDescriptor(\n        private val builtIns: KotlinBuiltIns,\n        override val fqName: FqName,\n        override val allValueArguments: Map<Name, ConstantValue<*>>\n) : AnnotationDescriptor {\n    override val type: KotlinType by lazy(PUBLICATION) {\n        builtIns.getBuiltInClassByFqName(fqName).defaultType\n    }");
        return (KotlinType) value;
    }

    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(sourceElement, "NO_SOURCE");
        return sourceElement;
    }
}
