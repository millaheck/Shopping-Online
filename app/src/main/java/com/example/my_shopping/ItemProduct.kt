package com.example.my_shopping

import android.os.Parcel
import android.os.Parcelable

class ItemProduct(
    var id: Int,
    var name: String?,
    var price: Double,
    var description: String?,
    var category: String?,
    var image: String?,
    var rating: ItemRating
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(ItemRating::class.java.classLoader)!!
    )

    override fun toString(): String {
        return "ItemProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ItemProduct> = object : Parcelable.Creator<ItemProduct> {
            override fun createFromParcel(parcel: Parcel): ItemProduct {
                return ItemProduct(parcel)
            }

            override fun newArray(size: Int): Array<ItemProduct?> {
                return arrayOfNulls(size)
            }
        }
    }
}

class ItemRating(var rate: Double, var count: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun toString(): String {
        return "ItemRating{" +
                "rate=" + rate +
                ", count=" + count +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(rate)
        parcel.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ItemRating> = object : Parcelable.Creator<ItemRating> {
            override fun createFromParcel(parcel: Parcel): ItemRating {
                return ItemRating(parcel)
            }

            override fun newArray(size: Int): Array<ItemRating?> {
                return arrayOfNulls(size)
            }
        }
    }
}
