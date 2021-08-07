package `in`.codepredators.vedanta.models

import `in`.codepredators.vedanta.converters.StringTypeConverter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.jetbrains.annotations.NotNull

@Entity(tableName = "medicine")
class Medicine {
    @NotNull
    @PrimaryKey(autoGenerate = true)
    var med_key:Int=0
    @ColumnInfo(name = "NAME")
    var name: String =""
    @ColumnInfo(name = "ID")
    var id: String=""
    @ColumnInfo(name = "BRAND")
    var brand: String =""
    @ColumnInfo(name = "COMPOSITION")
    @TypeConverters(StringTypeConverter::class)
    lateinit var composition: List<String>
    @ColumnInfo(name = "TARGET_DISEASE")
    @TypeConverters(StringTypeConverter::class)
    lateinit var targetDisease: List<String>
    @ColumnInfo(name = "DOSE")
    var dose: String=""
}